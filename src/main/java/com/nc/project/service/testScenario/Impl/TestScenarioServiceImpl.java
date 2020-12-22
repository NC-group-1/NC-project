package com.nc.project.service.testScenario.Impl;

import com.nc.project.dao.compound.CompoundDao;
import com.nc.project.dao.testScenario.TestScenarioDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;
import com.nc.project.model.TestScenario;
import com.nc.project.model.TestScenarioComponent;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
    public void createTestScenario(TestScenario testScenario) {
        int testScenarioId = testScenarioDao.create(testScenario);
        testScenarioDao.addManyActionOrCompound(testScenario.getListActionCompoundId().stream().mapToInt(i -> i).toArray(), testScenarioId);
    }

    @Override
    public Page<TestScenarioDto> getAllByPage(int page, int size, String filterBy, String filter, String orderBy, String order, int projectId) {
        switch (orderBy){
            case "name":
                orderBy ="t.name";
                break;
            case "creatorName":
                orderBy ="u.name";
                break;
            case "description":
                orderBy ="description";
                break;
            default:
                orderBy = "test_scenario_id";
                break;
        }
        switch (filterBy){
            case "creatorName":
                filterBy ="CONCAT(u.name, ' ',u.surname)";
                break;
            case "description":
                filterBy ="description";
                break;
            default:
                filterBy = "t.name";
                break;
        }
        if (!order.toLowerCase().equals("desc"))
            order = "";



        if (projectId != 0) {
            return new Page<>(testScenarioDao.getAllByPageAndProject(page, size, filterBy, filter, orderBy, order, projectId), testScenarioDao.getSizeOfProjectResultSet(filterBy, filter, projectId));
        }
        return new Page<>(testScenarioDao.getAllByPage(page, size,filterBy, filter, orderBy, order), testScenarioDao.getSizeOfResultSet(filterBy, filter));
    }

    @Override
    @Transactional
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
    @Transactional
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
            testScenario.setActions(getComponents(id));
            return testScenario;
        }
        return new TestScenario();
    }

    private List<TestScenarioComponent> getComponents(int id) {
        List<TestScenarioComponent> components = testScenarioDao.getComponents(id);
        for (TestScenarioComponent component : components) {
            if (component.getAction().getType() == ActionType.COMPOUND) {
                component
                        .getAction()
                        .setActions(compoundDao.getActionsOfCompound(component.getAction().getId()));
            }
        }
        return components;
    }

    @Override
    public TestScenario getDecomposedTestScenarioById(int id) {
        Optional<TestScenario> testScenarioOptional = testScenarioDao.getById(id);
        if (testScenarioOptional.isPresent()) {
            TestScenario testScenario = testScenarioOptional.get();
            testScenario.setActions(getDecomposedComponents(id));
            return testScenario;
        }
        return new TestScenario();
    }

    private List<TestScenarioComponent> getDecomposedComponents(int id) {
        List<TestScenarioComponent> components = new ArrayList<>();
        int order_num = 1;
        for (TestScenarioComponent component : testScenarioDao.getComponents(id)) {
            if (component.getAction().getType() == ActionType.COMPOUND) {
                for (ActionOfCompound action : compoundDao.getActionsOfCompound(component.getAction().getId())) {
                    components.add(new TestScenarioComponent(   action.getAction(), order_num++));
                }
            } else {
                component.setOrderNum(order_num++);
                components.add(component);
            }
        }
        return components;
    }
}
