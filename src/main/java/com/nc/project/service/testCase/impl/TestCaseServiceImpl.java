package com.nc.project.service.testCase.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.dao.user.UserDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.TestCase;
import com.nc.project.model.TestScenario;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseDao testCaseDao;
    private final ActionInstDao actionInstDao;
    private final DataSetDao dataSetDao;
    private final UserDao userDao;

    public TestCaseServiceImpl(TestCaseDao testCaseDao, ActionInstDao actionInstDao,
                               DataSetDao dataSetDao, UserDao userDao) {
        this.testCaseDao = testCaseDao;
        this.actionInstDao = actionInstDao;
        this.dataSetDao = dataSetDao;
        this.userDao = userDao;
    }

    @Override
    public Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        if(orderBy.equals(""))
            orderBy = "test_case_id";
        if(!order.equals("DESC")){
            order="";
        }

        return new Page(testCaseDao.getAllByPage(page,size,filter,orderBy,order),testCaseDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void deleteTestCase(int id) {
        testCaseDao.delete(id);
    }

    @Override
    public TestCase create(TestScenarioDto testScenarioDto) {
        TestCase testCase = TestCase.builder()
                .name(testScenarioDto.getName())
                .description(testScenarioDto.getDescription())
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .status(TestingStatus.UNKNOWN.name())
                .project(testScenarioDto.getProjectId())
                .creator(testScenarioDto.getCreatorId())
                .testScenario(testScenarioDto.getTest_scenario_id())
                .build();

        testCase = testCaseDao.create(testCase);

        List<ActionInst> actionInstances = convertToInstances(testScenarioDto, testCase.getId());
        actionInstances.forEach(actionInstDao::create);

        return testCase;
    }

    private List<ActionInst> convertToInstances(TestScenarioDto testScenarioDto, Integer testCaseId) {
        return testScenarioDto.getActions().stream()
                .map(a -> ActionInst.builder()
                        .action(a.getActionId())
                        .testCase(testCaseId)
                        .status(TestingStatus.UNKNOWN.name())
                        .orderNum(a.getOrderNum())
                        .build())
                .collect(Collectors.toList());

    }


}
