
package com.nc.project.service.testCase;

import com.nc.project.dto.*;
import com.nc.project.model.TestCase;
import java.util.List;
import java.util.Optional;

public interface TestCaseService {
    Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId);

    List<Integer> getTestCasesIdByWatcher(Integer userId);

    TestCase create(TestScenarioDto testScenarioDto);

    void updateTestCase(TestCase testCase);

    Optional<TestCase> findById(Integer id);

    void deleteTestCase(int test_case_id);

    List<ActionInstResponseDto> getAllInstances(Integer testCaseId);

    List<ActionInstRunDto> getAllActionInstRunDtos(Integer testCaseId);

    Optional<TestCaseDetailsDto> getTestCaseDetailsById(Integer id);

    Boolean editTestCaseActions(TestScenarioDto testScenarioDto);

    Page<TestCaseHistory> getHistory(int pageIndex, int pageSize, String filter, String orderBy, String order, int projectId);

    Page<TestCaseDetailsDto> getTestCasesPaginatedByUserId(int page, int size, String orderBy, String order, int userId);

    TestCaseStatisticDto getTestCaseStatistic(int userId);
}
