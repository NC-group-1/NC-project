package com.nc.project.dao.testScenario;


import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.model.TestScenarioComponent;

import java.util.List;
import java.util.Optional;

public interface TestScenarioDao {
    Integer create(TestScenario testScenario);

    void addActionOrCompound(int action_compound_id, int ts_id, int order_num);

    void addManyActionOrCompound(int[] action_compound_id, int ts_id);

    List<TestScenarioDto> getAllByPage(int page, int size,String filterBy,  String filter, String orderBy, String order);

    void edit(TestScenario testScenario);

    Integer getSizeOfResultSet(String filterBy, String filter);

    List<TestScenarioDto> getAllByPageAndProject(int page, int size,String filterBy, String filter, String orderBy, String order, int projectId);

    Integer getSizeOfProjectResultSet(String filterBy, String filter, int projectId);

    Boolean checkForTestCaseOnIt(int testScenarioId);

    void dropActionOrCompound(int testScenarioId);

    void delete(int testScenarioId);

    void makeUnactivated(int testScenarioId);

    Optional<TestScenario> getById(int id);

    List<TestScenarioComponent> getComponents(int id);
}
