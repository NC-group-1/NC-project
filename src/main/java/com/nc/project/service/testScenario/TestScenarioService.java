package com.nc.project.service.testScenario;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import org.springframework.http.ResponseEntity;

public interface TestScenarioService {
    void createTestScenario(TestScenario testScenario);
    Page<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order,int projectId);
    void editTestScenario(TestScenario testScenario);

    void deleteTestScenario(int testScenarioId);

    TestScenario getTestScenarioById(int id);
}
