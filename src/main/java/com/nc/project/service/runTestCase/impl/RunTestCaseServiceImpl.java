package com.nc.project.service.runTestCase.impl;


import com.nc.project.model.TestCase;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.runTestCase.RunTestCaseService;
import com.nc.project.service.runTestCase.TestCaseOperations;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RunTestCaseServiceImpl implements RunTestCaseService {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final RunAsyncServiceImpl runAsyncService;
    private final SharedContainerServiceImpl sharedContainerService;

    public RunTestCaseServiceImpl(ThreadPoolTaskScheduler threadPoolTaskScheduler,
                                  RunAsyncServiceImpl runAsyncService,
                                  SharedContainerServiceImpl sharedContainerService) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.runAsyncService = runAsyncService;
        this.sharedContainerService = sharedContainerService;
        WebDriverManager.chromedriver().setup();
    }

    private int runTestCase(Integer testCaseId, Integer startedById) {
        Optional<TestCase> testCase = runAsyncService.prepareForRun(testCaseId, startedById);
        if (testCase.isPresent()) {
            runAsyncService.runAsync(testCase.get());
            return 0;
        }
        return -1;
    }

    private int scheduleTestCase(Integer testCaseId, Integer startedById) {
        Optional<TestCase> testCase = runAsyncService.prepareForSchedule(testCaseId, startedById);
        if (testCase.isPresent()) {
            threadPoolTaskScheduler.schedule(
                    () -> runAsyncService.prepareForRun(testCaseId, startedById).ifPresent(runAsyncService::runAsync),
                    testCase.get().getStartDate()
            );
            return 0;
        }
        return -1;
    }

    private int suspendTestCase(Integer testCaseId) {
        if (sharedContainerService.getFromTargetStatuses(testCaseId) != TestingStatus.IN_PROGRESS) {
            return -1;
        } else {
            sharedContainerService.putToTargetStatuses(testCaseId, TestingStatus.STOPPED);
            return 0;
        }
    }

    private int resumeTestCase(Integer testCaseId) {
        if (sharedContainerService.getFromTargetStatuses(testCaseId) != TestingStatus.STOPPED) {
            return -1;
        } else {
            sharedContainerService.putToTargetStatuses(testCaseId, TestingStatus.IN_PROGRESS);
            synchronized (sharedContainerService.getFromSharedStorage(testCaseId)) {
                sharedContainerService.getFromSharedStorage(testCaseId).notifyAll();
            }
            return 0;
        }
    }

    private int interruptTestCase(Integer testCaseId) {
        if (sharedContainerService.getFromTargetStatuses(testCaseId) != TestingStatus.IN_PROGRESS &&
                sharedContainerService.getFromTargetStatuses(testCaseId) != TestingStatus.STOPPED) {
            return -1;
        } else {
            sharedContainerService.putToTargetStatuses(testCaseId, TestingStatus.CANCELED);
            synchronized (sharedContainerService.getFromSharedStorage(testCaseId)) {
                sharedContainerService.getFromSharedStorage(testCaseId).notifyAll();
            }
            return 0;
        }
    }

    @Override
    public int performTestCaseOperation(TestCaseOperations operation, Integer testCaseId, Integer startedById) {
        synchronized (testCaseId.toString().intern()) {
            switch (operation) {
                case RUN:
                    return runTestCase(testCaseId, startedById);
                case SCHEDULE:
                    return scheduleTestCase(testCaseId, startedById);
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
    }
}
