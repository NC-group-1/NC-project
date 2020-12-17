
package com.nc.project.service.testCase;

import com.nc.project.dto.ActionInstResponseDto;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestCase;

import java.util.List;
import java.util.Optional;

public interface TestCaseService {
    Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    List<Integer> getTestCasesIdByWatcher(Integer userId);

    TestCase create(TestScenarioDto testScenarioDto);

    Optional<TestCase> findById(Integer id);

    void editTestCase(TestCase testCase);

    void deleteTestCase(int test_case_id);

    List<ActionInstResponseDto> getAllInstances(Integer testCaseId);

    Boolean editTestCaseActions(TestScenarioDto testScenarioDto);
}
