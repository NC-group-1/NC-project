package com.nc.project.service.runTestCase;


public interface RunTestCaseService {

    /**
     * performs requested operation with test case
     *
     * @param operation operation type
     * @param testCaseId testCaseId id of test case that would be started
     * @param startedById startedById id of user that started/scheduled test case or 0 otherwise
     * @return 0 if operation performed or -1 if not
     */
    int performTestCaseOperation(TestCaseOperations operation, Integer testCaseId, Integer startedById);
}
