package com.nc.project.service.runTestCase.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.TestCase;
import com.nc.project.model.util.NotificationType;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.selenium.Context;
import com.nc.project.selenium.Invoker;
import com.nc.project.selenium.SeleniumExecutorImpl;
import com.nc.project.service.notification.NotificationService;
import com.nc.project.service.runTestCase.RunTestCaseService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class RunTestCaseServiceImpl implements RunTestCaseService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentHashMap<Integer, CopyOnWriteArrayList<ActionInstRunDto>> sharedStorage;
    private final ConcurrentHashMap<Integer, TestingStatus> targetStatuses;
    private final ActionInstDao actionInstDao;
    private final TestCaseDao testCaseDao;
    private final RunTestCaseServiceImpl runTestCaseService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    public RunTestCaseServiceImpl(ActionInstDao actionInstDao,
                                  TestCaseDao testCaseDao,
                                  @Lazy RunTestCaseServiceImpl runTestCaseService,
                                  NotificationService notificationService,
                                  SimpMessagingTemplate messagingTemplate,
                                  ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.actionInstDao = actionInstDao;
        this.testCaseDao = testCaseDao;
        this.runTestCaseService = runTestCaseService;
        this.notificationService = notificationService;
        this.messagingTemplate = messagingTemplate;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        WebDriverManager.chromedriver().setup();
        sharedStorage = new ConcurrentHashMap<>();
        targetStatuses = new ConcurrentHashMap<>();
    }

    @Transactional
    protected int runTestCase(Integer testCaseId, Integer startedById) {
        synchronized (testCaseId.toString().intern()) {
            TestCase testCase = testCaseDao.findById(testCaseId).orElseThrow();
            if (testCase.getStatus() != TestingStatus.READY && testCase.getStatus() != TestingStatus.SCHEDULED) {
                return -1;
            }
            testCase.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
            testCase.setStarter(startedById);
            testCase.setStatus(TestingStatus.IN_PROGRESS);
            testCaseDao.editForRun(testCase);
            targetStatuses.put(testCase.getId(), TestingStatus.IN_PROGRESS);
            sharedStorage.put(testCase.getId(), new CopyOnWriteArrayList<>());
            runTestCaseService.runAsync(testCase);
            return 0;
        }
    }

    @Transactional
    protected int scheduleTestCase(Integer testCaseId, Integer startedById) {
        synchronized (testCaseId.toString().intern()) {
            TestCase testCase = testCaseDao.findById(testCaseId).orElseThrow();
            if (testCase.getStatus() != TestingStatus.READY || testCase.getStartDate() == null) {
                return -1;
            }
            testCase.setStarter(startedById);
            testCase.setStatus(TestingStatus.SCHEDULED);
            testCaseDao.editForRun(testCase);
            threadPoolTaskScheduler.schedule(
                    () -> runTestCaseService.runTestCase(testCaseId, startedById),
                    testCase.getStartDate()
            );
            return 0;
        }
    }

    private int suspendTestCase(Integer testCaseId) {
        synchronized (testCaseId.toString().intern()) {
            if(targetStatuses.get(testCaseId) != TestingStatus.IN_PROGRESS){
                return -1;
            } else {
                targetStatuses.put(testCaseId, TestingStatus.STOPPED);
                return 0;
            }
        }
    }

    private int resumeTestCase(Integer testCaseId) {
        synchronized (testCaseId.toString().intern()) {
            if(targetStatuses.get(testCaseId) != TestingStatus.STOPPED){
                return -1;
            } else {
                targetStatuses.put(testCaseId, TestingStatus.IN_PROGRESS);
                synchronized (sharedStorage.get(testCaseId)){
                    sharedStorage.get(testCaseId).notifyAll();
                }
                return 0;
            }
        }
    }

    private int interruptTestCase(Integer testCaseId) {
        synchronized (testCaseId.toString().intern()) {
            if(targetStatuses.get(testCaseId) != TestingStatus.IN_PROGRESS &&
                    targetStatuses.get(testCaseId) != TestingStatus.STOPPED){
                return -1;
            } else {
                targetStatuses.put(testCaseId, TestingStatus.CANCELED);
                synchronized (sharedStorage.get(testCaseId)){
                    sharedStorage.get(testCaseId).notifyAll();
                }
                return 0;
            }
        }
    }

    @Override
    public int performTestCaseOperation(TestCaseOperations operation, Integer testCaseId, Integer startedById) {
        switch (operation) {
            case RUN:
                return runTestCaseService.runTestCase(testCaseId, startedById);
            case SCHEDULE:
                return runTestCaseService.scheduleTestCase(testCaseId, startedById);
            case STOP:
                return suspendTestCase(testCaseId);
            case RESUME:
                return resumeTestCase(testCaseId);
            case CANCEL:
                return interruptTestCase(testCaseId);
            default:
                return -1;
        }
    }

    @Override
    public List<ActionInstRunDto> getActionInstRunDtosFromSharedStorage(Integer testCaseId) {
        return sharedStorage.get(testCaseId);
    }

    @Override
    public void sendActionInstToTestCaseSocket(List<ActionInstRunDto> actionInstRunDtos, Integer testCaseId) {
        messagingTemplate.convertAndSend("/topic/actionInst/" + testCaseId, actionInstRunDtos);
    }

    @Override
    public void sendActionInstToTestCaseSocket(Integer testCaseId) {
        messagingTemplate.convertAndSend("/topic/actionInst/" + testCaseId, sharedStorage.get(testCaseId));
    }

    @Async
    protected void runAsync(TestCase testCase){
        log.debug("Run test case with id="+testCase.getId()+" asynchronously. Thread name="
                +Thread.currentThread().getName());
        WebDriver driver = setup();
        try {
            createStatusNotification(testCase);
            driver.get(testCaseDao.getProjectLinkByTestCaseId(testCase.getId()).orElseThrow());
            List<ActionInstRunDto> actionInstRunDtos = actionInstDao.getAllActionInstRunDtosByTestCaseId(testCase.getId());
            Invoker invoker = new Invoker(new SeleniumExecutorImpl(driver, getContext(actionInstRunDtos)));
            for (int actionNumber = 0; actionNumber < actionInstRunDtos.size(); actionNumber++) {
                ActionInstRunDto actionInst = actionInstRunDtos.get(actionNumber);
                Thread.sleep(1000);
                TestingStatus currentActionInstStatus = invoker.invoke(actionInst.getActionType(),
                        actionInst.getParameterValue(),
                        actionInst.getId());
                actionInst.setStatus(currentActionInstStatus);
                sharedStorage.get(testCase.getId()).add(actionInst);
                runTestCaseService.sendActionInstToTestCaseSocket(Collections.singletonList(actionInst), testCase.getId());
                testCase.setStatus(targetStatuses.get(testCase.getId()));
                if (currentActionInstStatus == TestingStatus.FAILED) {
                    testCase.setStatus(TestingStatus.FAILED);
                }
                notificationService.sendProgressToTestCase(new TestCaseProgress(testCase.getId(),testCase.getName(),
                        testCase.getStatus(),(actionNumber+1.0f)/actionInstRunDtos.size()));
                while (targetStatuses.get(testCase.getId()) == TestingStatus.STOPPED
                        && testCase.getStatus() != TestingStatus.CANCELED
                        && testCase.getStatus() != TestingStatus.FAILED){
                    synchronized (sharedStorage.get(testCase.getId())){
                        testCaseDao.editForRun(testCase);
                        sharedStorage.get(testCase.getId()).wait();
                        testCase.setStatus(TestingStatus.IN_PROGRESS);
                        testCaseDao.editForRun(testCase);
                    }
                }
                if (testCase.getStatus() == TestingStatus.FAILED || testCase.getStatus() == TestingStatus.CANCELED) {
                    break;
                }
            }
            actionInstRunDtos.stream().filter(actionInstRunDto -> actionInstRunDto.getStatus() == TestingStatus.UNKNOWN)
                    .forEach(actionInstRunDto -> actionInstRunDto.setStatus(TestingStatus.NOT_STARTED));
            if(testCase.getStatus() == TestingStatus.IN_PROGRESS || testCase.getStatus() == TestingStatus.STOPPED){
                testCase.setStatus(TestingStatus.PASSED);
            }
            testCase.setFinishDate(Timestamp.valueOf(LocalDateTime.now()));
            testCaseDao.editForRun(testCase);
            actionInstDao.updateAll(actionInstRunDtos);
            createStatusNotification(testCase);
        } catch (Exception e){
            log.error("Exception in run test case async thread", e);
            testCase.setStatus(TestingStatus.UNKNOWN);
            testCaseDao.editForRun(testCase);
            createStatusNotification(testCase);
        } finally {
            sharedStorage.remove(testCase.getId());
            targetStatuses.remove(testCase.getId());
            driver.close();
        }
    }

    private void createStatusNotification(TestCase testCase){
        switch (testCase.getStatus()) {
            case PASSED:
                notificationService.createNotification(testCase.getId(), NotificationType.SUCCESS);
                break;
            case FAILED:
            case UNKNOWN:
                notificationService.createNotification(testCase.getId(), NotificationType.FAIL);
                break;
            case IN_PROGRESS:
                notificationService.createNotification(testCase.getId(), NotificationType.STARTED);
                break;
            case CANCELED:
                notificationService.createNotification(testCase.getId(), NotificationType.CANCEL);
                break;
        }
    }

    private WebDriver setup(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    private Context getContext(List<ActionInstRunDto> actionInstRunDtos){
        return new Context() {
            @Override
            public void put(Integer actionId, String value) {
                actionInstRunDtos.stream()
                        .filter(actionInstRunDto -> actionInstRunDto.getId().equals(actionId))
                        .findFirst().ifPresent(actionInstRunDto -> actionInstRunDto.setResult(value));
            }
            @Override
            public Optional<String> get(Integer actionId) {
                ActionInstRunDto currentAction = actionInstRunDtos.stream()
                        .filter(actionInstRunDto -> actionInstRunDto.getId().equals(actionId))
                        .findFirst().orElseThrow();
                return actionInstRunDtos.stream()
                        .filter(actionInstRunDto -> actionInstRunDto.getActionType().getContextBehaviour() == 1)
                        .filter(actionInstRunDto -> actionInstRunDto.getParameterKeyKey()
                                .equals(currentAction.getParameterKeyKey()))
                        .map(ActionInstRunDto::getResult).findFirst();
            }
        };
    }
}
