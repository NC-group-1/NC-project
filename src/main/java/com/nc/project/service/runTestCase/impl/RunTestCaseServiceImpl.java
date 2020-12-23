package com.nc.project.service.runTestCase.impl;


import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.model.TestCase;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.runTestCase.RunTestCaseService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RunTestCaseServiceImpl implements RunTestCaseService {

    private final ConcurrentHashMap<Integer, CopyOnWriteArrayList<ActionInstRunDto>> sharedStorage;
    private final ConcurrentHashMap<Integer, TestingStatus> targetStatuses;
    private final SimpMessagingTemplate messagingTemplate;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final RunAsyncService runAsyncService;

    public RunTestCaseServiceImpl(SimpMessagingTemplate messagingTemplate,
                                  ThreadPoolTaskScheduler threadPoolTaskScheduler,
                                  @Lazy RunAsyncService runAsyncService) {
        this.messagingTemplate = messagingTemplate;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.runAsyncService = runAsyncService;
        WebDriverManager.chromedriver().setup();
        sharedStorage = new ConcurrentHashMap<>();
        targetStatuses = new ConcurrentHashMap<>();
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
        if (targetStatuses.get(testCaseId) != TestingStatus.IN_PROGRESS) {
            return -1;
        } else {
            targetStatuses.put(testCaseId, TestingStatus.STOPPED);
            return 0;
        }
    }

    private int resumeTestCase(Integer testCaseId) {
        if (targetStatuses.get(testCaseId) != TestingStatus.STOPPED) {
            return -1;
        } else {
            targetStatuses.put(testCaseId, TestingStatus.IN_PROGRESS);
            synchronized (sharedStorage.get(testCaseId)) {
                sharedStorage.get(testCaseId).notifyAll();
            }
            return 0;
        }
    }

    private int interruptTestCase(Integer testCaseId) {
        if (targetStatuses.get(testCaseId) != TestingStatus.IN_PROGRESS &&
                targetStatuses.get(testCaseId) != TestingStatus.STOPPED) {
            return -1;
        } else {
            targetStatuses.put(testCaseId, TestingStatus.CANCELED);
            synchronized (sharedStorage.get(testCaseId)) {
                sharedStorage.get(testCaseId).notifyAll();
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

    protected TestingStatus getFromTargetStatuses(Integer testCaseId) {
        return targetStatuses.get(testCaseId);
    }

    protected void initInSharedMaps(Integer testCaseId) {
        targetStatuses.put(testCaseId, TestingStatus.IN_PROGRESS);
        sharedStorage.put(testCaseId, new CopyOnWriteArrayList<>());
    }

    protected void deleteFromSharedMaps(Integer testCaseId) {
        sharedStorage.remove(testCaseId);
        targetStatuses.remove(testCaseId);
    }
}
