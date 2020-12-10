package com.nc.project.service.testScenario.Impl;

import com.nc.project.dao.compound.CompoundDao;
import com.nc.project.dao.testScenario.TestScenarioDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.model.TestScenarioComponent;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {
    private final TestScenarioDao testScenarioDao;
    private final CompoundDao compoundDao;

    public TestScenarioServiceImpl(TestScenarioDao testScenarioDao, CompoundDao compoundDao) {
        this.testScenarioDao = testScenarioDao;
        this.compoundDao = compoundDao;
    }

    @Override
    public void createTestScenario(TestScenario testScenario) {
        int testScenarioId = testScenarioDao.create(testScenario);
        testScenarioDao.addManyActionOrCompound(testScenario.getListActionCompoundId().stream().mapToInt(i -> i).toArray(), testScenarioId);
    }

    @Override
    public Page<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId) {
        if (orderBy.equals(""))
            orderBy = "test_scenario_id";
        if (!order.equals("DESC"))
            order = "";
        if (projectId != 0) {
            return new Page<>(testScenarioDao.getAllByPageAndProject(page, size, filter, orderBy, order, projectId), testScenarioDao.getSizeOfProjectResultSet(filter, projectId));
        }
        return new Page<>(testScenarioDao.getAllByPage(page, size, filter, orderBy, order), testScenarioDao.getSizeOfResultSet(filter));
    }

    @Override
    public void editTestScenario(TestScenario testScenario) {
        int testScenarioId = testScenario.getTestScenarioId();
        if (!testScenarioDao.checkForTestCaseOnIt(testScenarioId)) {
            testScenarioDao.dropActionOrCompound(testScenarioId);
            testScenarioDao.addManyActionOrCompound(testScenario.getListActionCompoundId().stream().mapToInt(i -> i).toArray(), testScenarioId);
            testScenarioDao.edit(testScenario);
        } else {
            this.createTestScenario(testScenario);
        }

    }

    @Override
    public void deleteTestScenario(int testScenarioId) {
        if (!testScenarioDao.checkForTestCaseOnIt(testScenarioId)) {
            testScenarioDao.dropActionOrCompound(testScenarioId);
            testScenarioDao.delete(testScenarioId);
        } else {
            testScenarioDao.makeUnactivated(testScenarioId);
        }
    }

    @Override
    public TestScenario getTestScenarioById(int id) {
        Optional<TestScenario> testScenarioOptional = testScenarioDao.getById(id);
        if (testScenarioOptional.isPresent()) {
            TestScenario testScenario = testScenarioOptional.get();
            List<TestScenarioComponent> components = testScenarioDao.getComponents(id);
            for (TestScenarioComponent component : components) {
                if (component.getAction().getType() == ActionType.COMPOUND) {
                    component
                            .getAction()
                            .setActions(compoundDao.getActionsOfCompound(component.getAction().getId()));
                }
            }
            testScenario.setActions(components);
            return testScenario;
        }
        return new TestScenario();
    }
}
