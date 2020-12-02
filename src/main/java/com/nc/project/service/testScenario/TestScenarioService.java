package com.nc.project.service.testScenario;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;

public interface TestScenarioService {
    void createTestScenario(TestScenario testScenario);
    Page<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order,int projectId);
    void editTestScenario(TestScenario testScenario);
}
