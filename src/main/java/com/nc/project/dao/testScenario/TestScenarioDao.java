package com.nc.project.dao.testScenario;


import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;

import java.util.List;
import java.util.Optional;

public interface TestScenarioDao {
    Optional<Integer> create(TestScenario testScenario);
    void addActionOrCompound(int action_compound_id,int ts_id,int order_num);
    void addManyActionOrCompound(int action_compound_id,int ts_id,int order_num);
    List<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void edit(TestScenario testScenario);
    Optional<Integer> getSizeOfResultSet(String filter);
    List<TestScenarioDto> getAllByPageAndProject(int page, int size, String filter, String orderBy, String order,int projectId);
    Optional<Integer> getSizeOfProjectResultSet(String filter,int projectId);
    Optional<Boolean> checkForTestCaseOnIt(int testScenarioId);
    void dropActionOrCompound(int testScenarioId);

    void delete(int testScenarioId);

    void makeUnactivated(int testScenarioId);

    Optional<TestScenario> getById(int id);
}
