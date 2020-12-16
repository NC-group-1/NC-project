package com.nc.project.service.testCase.impl;

import com.nc.project.dao.action.ActionDao;
import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dao.parameterKey.ParameterKeyDao;
import com.nc.project.dao.runningTestCase.RunningTestCaseDao;
import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.dao.user.UserDao;
import com.nc.project.dto.*;
import com.nc.project.model.*;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    private final TestCaseDao testCaseDao;
    private final ActionInstDao actionInstDao;
    private final ActionDao actionDao;
    private final ParameterKeyDao parameterKeyDao;
    private final RunningTestCaseDao runningTestCaseDao;
    private final DataSetDao dataSetDao;
    private final UserDao userDao;

    public TestCaseServiceImpl(TestCaseDao testCaseDao, ActionInstDao actionInstDao,
                               ActionDao actionDao, ParameterKeyDao parameterKeyDao,
                               RunningTestCaseDao runningTestCaseDao, DataSetDao dataSetDao, UserDao userDao) {
        this.testCaseDao = testCaseDao;
        this.actionInstDao = actionInstDao;
        this.actionDao = actionDao;
        this.parameterKeyDao = parameterKeyDao;
        this.runningTestCaseDao = runningTestCaseDao;
        this.dataSetDao = dataSetDao;
        this.userDao = userDao;
    }

    @Override
    public Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        if (orderBy.equals(""))
            orderBy = "test_case_id";
        if (!order.equals("DESC")) {
            order = "";
        }

        return new Page(testCaseDao.getAllByPage(page, size, filter, orderBy, order), testCaseDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void editTestCase(TestCase testCase) {
        testCaseDao.edit(testCase);
    }

    @Override
    public Optional<TestCase> findById(Integer id) {
        return testCaseDao.findById(id);
    }

    @Override
    public void deleteTestCase(int test_case_id) {
        testCaseDao.delete(test_case_id);
    }

    @Override
    public List<ActionInstResponseDto> getAllInstances(Integer testCaseId) {
        List<ActionInstResponseDto> responseDtos = new ArrayList<>();
        List<ActionInst> instances = actionInstDao.getAllActionInstancesByTestCaseId(testCaseId);
        for (ActionInst a : instances) {
            ActionInstResponseDto responseDto = new ActionInstResponseDto();
            responseDto.setId(a.getId());
            responseDto.setOrderNum(a.getOrderNum());
            responseDto.setParameterKey(parameterKeyDao.findById(a.getParameterKey().getId()).orElse(null));
            responseDto.setAction(actionDao.findById(a.getAction()).orElse(null));
            responseDto.setDatasetId(a.getDataSet());
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    @Override
    public List<ActionInstRunDto> getAllActionInstRunDtos(Integer testCaseId) {
        return actionInstDao.getAllActionInstRunDtosByTestCaseId(testCaseId);
    }

    @Override
    public Optional<TestCaseDetailsDto> getTestCaseDetailsById(Integer id) {
        Optional<TestCaseDetailsDto> optionalTestCase = testCaseDao.getTestCaseDetailsById(id);
        optionalTestCase.ifPresent(testCaseDetailsDto -> testCaseDetailsDto.setWatchers(runningTestCaseDao.getWatcherByTestCaseId(id)));
        return optionalTestCase;
    }

    @Override
    public TestCase create(TestScenarioDto testScenarioDto) {
        TestCase testCase = TestCase.builder()
                .name(testScenarioDto.getName())
                .description(testScenarioDto.getDescription())
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .status(TestingStatus.UNKNOWN)
                .creator(testScenarioDto.getUser().getId())
                .testScenario(testScenarioDto.getTestScenarioId())
                .build();

        testCase = testCaseDao.create(testCase);

        List<ActionInst> actionInstances = convertToActionInstances(testScenarioDto, testCase.getId());

        actionInstances.forEach(actionInstDao::create);

        return testCase;
    }
    @Override
    public List<Integer> getTestCasesIdByWatcher(Integer userId) {
        return testCaseDao.getTestCasesIdByWatcher(userId);
    }


    private List<ActionInst> convertToActionInstances(TestScenarioDto testScenarioDto, Integer testCaseId) {
        return testScenarioDto.getActions().stream()
                .map(a -> ActionInst.builder()
                        .action(a.getAction().getId())
                        .testCase(testCaseId)
                        .status(TestingStatus.UNKNOWN.name())
                        .orderNum(a.getOrderNum())
                        .parameterKey(a.getParameterKey())
                        .dataSet(a.getDatasetId())
                        .build())
                .collect(Collectors.toList());
    }

    private Integer getDatasetIdForActionInstance(Action action, DataSet dataset) {
        List<Integer> keyIds = dataset.getParameters().stream()
                .map(Parameter::getKey)
                .map(ParameterKey::getId)
                .collect(Collectors.toList());

        return keyIds.contains(action.getKey().getId()) ? dataset.getId() : null;
    }


}
