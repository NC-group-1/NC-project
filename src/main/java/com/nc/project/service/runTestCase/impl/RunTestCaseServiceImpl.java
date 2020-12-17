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
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
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

    private final ActionInstDao actionInstDao;
    private final TestCaseDao testCaseDao;
    private final RunTestCaseServiceImpl runTestCaseService;
    private final NotificationService notificationService;
    private final SimpMessagingTemplate messagingTemplate;

    public RunTestCaseServiceImpl(ActionInstDao actionInstDao,
                                  TestCaseDao testCaseDao,
                                  @Lazy RunTestCaseServiceImpl runTestCaseService,
                                  NotificationService notificationService, SimpMessagingTemplate messagingTemplate) {
        this.actionInstDao = actionInstDao;
        this.testCaseDao = testCaseDao;
        this.runTestCaseService = runTestCaseService;
        this.notificationService = notificationService;
        this.messagingTemplate = messagingTemplate;
        WebDriverManager.chromedriver().setup();
        sharedStorage = new ConcurrentHashMap<>();
    }

    @Override
    @Transactional
    public int runTestCase(Integer testCaseId, Integer startedById) {
        TestCase testCase = testCaseDao.findById(testCaseId).orElseThrow();
        if(testCase.getStatus() != TestingStatus.UNKNOWN){
            return -1;
        }
        testCase.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        testCase.setStarter(startedById);
        testCase.setStatus(TestingStatus.IN_PROGRESS);
        testCaseDao.editForRun(testCase);
        notificationService.createNotification(testCaseId, NotificationType.STARTED);
        sharedStorage.put(testCaseId, new CopyOnWriteArrayList<>());
        runTestCaseService.runAsync(testCase);
        return 0;
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
    @Transactional
    protected void runAsync(TestCase testCase){
        log.debug("Run test case with id="+testCase.getId()+" asynchronously. Thread name="
                +Thread.currentThread().getName());
        WebDriver driver = setup();
        try {
            driver.get(testCaseDao.getProjectLinkByTestCaseId(testCase.getId()).orElseThrow());
            List<ActionInstRunDto> actionInstRunDtos = actionInstDao.getAllActionInstRunDtosByTestCaseId(testCase.getId());
            Invoker invoker = new Invoker(new SeleniumExecutorImpl(driver, getContext(actionInstRunDtos)));
            TestCaseProgress progress = new TestCaseProgress(testCase.getId(),testCase.getName(),
                    testCase.getStatus(),0.0f);
            for (int actionNumber = 0; actionNumber < actionInstRunDtos.size(); actionNumber++) {
                ActionInstRunDto actionInst = actionInstRunDtos.get(actionNumber);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Thread sleep error", e);
                }
                TestingStatus currentActionInstStatus = invoker.invoke(actionInst.getActionType(),
                        actionInst.getParameterValue(),
                        actionInst.getId());
                actionInst.setStatus(currentActionInstStatus);
                progress.setProgress((actionNumber+1.0f)/actionInstRunDtos.size());
                notificationService.sendProgressToTestCase(progress);
                sharedStorage.get(testCase.getId()).add(actionInst);
                runTestCaseService.sendActionInstToTestCaseSocket(Collections.singletonList(actionInst), testCase.getId());
                if (currentActionInstStatus == TestingStatus.FAILED) {
                    testCase.setStatus(TestingStatus.FAILED);
                    break;
                }
            }
            if(testCase.getStatus() == TestingStatus.IN_PROGRESS){
                testCase.setStatus(TestingStatus.PASSED);
            }
            testCase.setFinishDate(Timestamp.valueOf(LocalDateTime.now()));
            testCaseDao.editForRun(testCase);
            actionInstDao.updateAll(actionInstRunDtos);
            if(testCase.getStatus() == TestingStatus.FAILED){
                notificationService.createNotification(testCase.getId(), NotificationType.FAIL);
            }
            if(testCase.getStatus() == TestingStatus.PASSED){
                notificationService.createNotification(testCase.getId(), NotificationType.SUCCESS);
            }
        } finally {
            sharedStorage.remove(testCase.getId());
            driver.close();
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
