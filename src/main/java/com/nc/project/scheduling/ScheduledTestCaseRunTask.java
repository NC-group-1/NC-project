package com.nc.project.scheduling;

import com.nc.project.service.runTestCase.RunTestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledTestCaseRunTask implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Integer testCaseId;
    private Integer startedById;
    private RunTestCaseService runTestCaseService;

    public ScheduledTestCaseRunTask(Integer testCaseId, Integer startedById, RunTestCaseService runTestCaseService) {
        this.testCaseId = testCaseId;
        this.startedById = startedById;
        this.runTestCaseService = runTestCaseService;
    }

    @Override
    public void run() {
        log.debug( "Scheduled test case run task. Thread name=" +Thread.currentThread().getName());
        runTestCaseService.runTestCase(testCaseId, startedById);
    }
}
