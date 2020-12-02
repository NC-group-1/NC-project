package com.nc.project.service.testScenario.Impl;

import com.nc.project.dao.testScenario.TestScenarioDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.stereotype.Service;

@Service
public class TestScenarioServiceImpl implements TestScenarioService {
    private TestScenarioDao testScenarioDao;

    public TestScenarioServiceImpl(TestScenarioDao testScenarioDao) {
        this.testScenarioDao = testScenarioDao;
    }

    @Override
    public void createTestScenario(TestScenario testScenario) {
        testScenarioDao.create(testScenario);
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
        testScenarioDao.edit(testScenario);
    }
}
