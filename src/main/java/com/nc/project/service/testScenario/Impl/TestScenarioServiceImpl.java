package com.nc.project.service.testScenario.Impl;

import com.nc.project.dao.testScenario.TestScenarioDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {
    private TestScenarioDao testScenarioDao;

    public TestScenarioServiceImpl(TestScenarioDao testScenarioDao) {
        this.testScenarioDao = testScenarioDao;
    }

    @Override
    public void createTestScenario(TestScenario testScenario) {

        Integer ts_id= testScenarioDao.create(testScenario).get();
        ArrayList<Integer> arrlist = testScenario.getAction_compound_id();
        for (int counter = 0; counter < arrlist.size(); counter++) {
            testScenarioDao.addActionOrCompound(arrlist.get(counter),ts_id,counter+1);
        }

    }

    @Override
    public Page<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order,int projectId) {
        if(orderBy.equals(""))
            orderBy = "test_scenario_id";
        if(!order.equals("DESC"))
            order="";
        if (projectId!=0)
            return new Page<>(testScenarioDao.getAllByPageAndProject(page,size,filter,orderBy,order,projectId),testScenarioDao.getSizeOfProjectResultSet(filter,projectId).get());
        return new Page<>(testScenarioDao.getAllByPage(page,size,filter,orderBy,order),testScenarioDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void editTestScenario(TestScenario testScenario) {
        if(testScenario.isActive()){
            if(!testScenarioDao.checkForTestCaseOnIt(testScenario.getTest_scenario_id()).get()){
                testScenarioDao.dropActionOrCompound(testScenario.getTest_scenario_id());
                ArrayList<Integer> arrlist = testScenario.getAction_compound_id();
                for (int counter = 0; counter < arrlist.size(); counter++) {
                    testScenarioDao.addActionOrCompound(arrlist.get(counter),testScenario.getTest_scenario_id(),counter+1);
                }
                testScenarioDao.edit(testScenario);
            }
            else
                this.createTestScenario(testScenario);
        }else
            testScenarioDao.edit(testScenario);

    }

    @Override
    public void deleteTestScenario(int testScenarioId) {
        if(!testScenarioDao.checkForTestCaseOnIt(testScenarioId).get()){
            testScenarioDao.dropActionOrCompound(testScenarioId);
            testScenarioDao.delete(testScenarioId);
        }else{
            testScenarioDao.makeUnactivated(testScenarioId);
        }
    }

    @Override
    public TestScenario getTestScenarioById(int id) {
        TestScenario testScenario=testScenarioDao.getById(id).get();
        return testScenario;
    }
}
