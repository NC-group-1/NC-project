package com.nc.project.dao.testScenario;


import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;

import java.util.List;
import java.util.Optional;

public interface TestScenarioDao {
    void create(TestScenario testScenario);
    List<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void edit(TestScenario testScenario);
    Optional<Integer> getSizeOfResultSet(String filter);
}
