
package com.nc.project.service.testCase;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestCase;
import com.nc.project.model.TestScenario;

public interface TestCaseService {
    Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    TestCase create(TestScenarioDto testScenarioDto);

    void deleteTestCase(int id);

}
