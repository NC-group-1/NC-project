package com.nc.project.service.runTestCase;

import com.nc.project.dto.ActionInstRunDto;
import java.util.List;

public interface RunTestCaseService {

    enum TestCaseOperations {RUN,SCHEDULE,STOP,RESUME,CANCEL}

    /**
     * performs requested operation with test case
     *
     * @param operation operation type
     * @param testCaseId testCaseId id of test case that would be started
     * @param startedById startedById id of user that started/scheduled test case or 0 otherwise
     * @return 0 if operation performed or -1 if not
     */
    int performTestCaseOperation(TestCaseOperations operation, Integer testCaseId, Integer startedById);

    List<ActionInstRunDto> getActionInstRunDtosFromSharedStorage(Integer testCaseId);

    void sendActionInstToTestCaseSocket(List<ActionInstRunDto> actionInstRunDtos, Integer testCaseId);

    void sendActionInstToTestCaseSocket(Integer testCaseId);
}
