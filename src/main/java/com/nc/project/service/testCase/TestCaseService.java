
package com.nc.project.service.testCase;

import com.nc.project.dto.ActionInstResponseDto;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestCase;

import java.util.List;

public interface TestCaseService {
    Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId);
    List<Integer> getTestCasesIdByWatcher(Integer userId);
    TestCase create(TestScenarioDto testScenarioDto);
    void updateTestCase(TestCase testCase);
    void deleteTestCase(int test_case_id);

    List<ActionInstResponseDto> getAllInstances(Integer testCaseId);

}
