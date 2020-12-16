package com.nc.project.service.runTestCase;

import com.nc.project.dto.ActionInstRunDto;
import java.util.List;

public interface RunTestCaseService {
    /**
     * Starts test case
     *
     * @param testCaseId id of test case that would be started
     * @param startedById id of user that started test case
     * @return 0 if test case started or -1 if test case already started or finished
     */
    int runTestCase(Integer testCaseId, Integer startedById);

    List<ActionInstRunDto> getActionInstRunDtosFromSharedStorage(Integer testCaseId);

    void sendActionInstToTestCaseSocket(List<ActionInstRunDto> actionInstRunDtos, Integer testCaseId);

    void sendActionInstToTestCaseSocket(Integer testCaseId);
}
