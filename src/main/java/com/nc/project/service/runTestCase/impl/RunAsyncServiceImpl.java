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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RunAsyncServiceImpl {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ActionInstDao actionInstDao;
    private final TestCaseDao testCaseDao;
    private final NotificationService notificationService;
    private final SharedContainerServiceImpl sharedContainerService;

    public RunAsyncServiceImpl(ActionInstDao actionInstDao,
                               TestCaseDao testCaseDao,
                               NotificationService notificationService,
                               SharedContainerServiceImpl sharedContainerService) {
        this.actionInstDao = actionInstDao;
        this.testCaseDao = testCaseDao;
        this.notificationService = notificationService;
        this.sharedContainerService = sharedContainerService;
    }

    @Transactional
    protected Optional<TestCase> prepareForRun(Integer testCaseId, Integer startedById) {
        Optional<TestCase> optionalTestCase = testCaseDao.findById(testCaseId);
        if (optionalTestCase.isEmpty() || (optionalTestCase.get().getStatus() != TestingStatus.READY
                && optionalTestCase.get().getStatus() != TestingStatus.SCHEDULED)) {
            return Optional.empty();
        }
        TestCase testCase = optionalTestCase.get();
        testCase.setStartDate(Timestamp.valueOf(LocalDateTime.now()));
        testCase.setStarter(startedById);
        testCase.setStatus(TestingStatus.IN_PROGRESS);
        testCaseDao.editForRun(testCase);
        sharedContainerService.initInSharedMaps(testCaseId);
        return Optional.of(testCase);
    }

    @Transactional
    protected Optional<TestCase> prepareForSchedule(Integer testCaseId, Integer startedById) {
        Optional<TestCase> optionalTestCase = testCaseDao.findById(testCaseId);
        if (optionalTestCase.isEmpty()
                || optionalTestCase.get().getStatus() != TestingStatus.READY
                || optionalTestCase.get().getStartDate() == null) {
            return Optional.empty();
        }
        TestCase testCase = optionalTestCase.get();
        testCase.setStarter(startedById);
        testCase.setStatus(TestingStatus.SCHEDULED);
        testCaseDao.editForRun(testCase);
        return Optional.of(testCase);
    }

    @Async
    protected void runAsync(TestCase testCase) {
        log.debug("Run test case with id=" + testCase.getId() + " asynchronously. Thread name="
                + Thread.currentThread().getName());
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
                sharedContainerService.getFromSharedStorage(testCase.getId()).add(actionInst);
                notificationService.sendActionInstToTestCaseSocket(Collections.singletonList(actionInst), testCase.getId());
                testCase.setStatus(sharedContainerService.getFromTargetStatuses(testCase.getId()));
                if (currentActionInstStatus == TestingStatus.FAILED) {
                    testCase.setStatus(TestingStatus.FAILED);
                }
                notificationService.sendProgressToTestCase(new TestCaseProgress(testCase.getId(), testCase.getName(),
                        testCase.getStatus(), (actionNumber + 1.0f) / actionInstRunDtos.size()));
                while (sharedContainerService.getFromTargetStatuses(testCase.getId()) == TestingStatus.STOPPED
                        && testCase.getStatus() != TestingStatus.CANCELED
                        && testCase.getStatus() != TestingStatus.FAILED) {
                    synchronized (sharedContainerService.getFromSharedStorage(testCase.getId())) {
                        testCaseDao.editForRun(testCase);
                        sharedContainerService.getFromSharedStorage(testCase.getId()).wait();
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
            if (testCase.getStatus() == TestingStatus.IN_PROGRESS || testCase.getStatus() == TestingStatus.STOPPED) {
                testCase.setStatus(TestingStatus.PASSED);
            }
            testCase.setFinishDate(Timestamp.valueOf(LocalDateTime.now()));
            testCaseDao.editForRun(testCase);
            actionInstDao.updateAll(actionInstRunDtos);
            createStatusNotification(testCase);
        } catch (Exception e) {
            log.error("Exception in run test case async thread", e);
            testCase.setStatus(TestingStatus.UNKNOWN);
            testCaseDao.editForRun(testCase);
            createStatusNotification(testCase);
        } finally {
            sharedContainerService.deleteFromSharedMaps(testCase.getId());
            driver.quit();
        }
    }

    private WebDriver setup() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    private Context getContext(List<ActionInstRunDto> actionInstRunDtos) {
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
                        .filter(actionInstRunDto -> actionInstRunDto.getActionType().getContextBehaviour().equals(1))
                        .filter(actionInstRunDto -> actionInstRunDto.getParameterKeyKey()
                                .equals(currentAction.getParameterKeyKey()))
                        .map(ActionInstRunDto::getResult).findFirst();
            }
        };
    }

    private void createStatusNotification(TestCase testCase) {
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
}
