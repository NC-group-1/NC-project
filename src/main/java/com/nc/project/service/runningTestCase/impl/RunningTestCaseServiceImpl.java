package com.nc.project.service.runningTestCase.impl;


import com.nc.project.dao.runningTestCase.RunningTestCaseDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;
import com.nc.project.service.runningTestCase.RunningTestCaseService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RunningTestCaseServiceImpl implements RunningTestCaseService {

    private final RunningTestCaseDao runningTestCaseDao;

    public RunningTestCaseServiceImpl(RunningTestCaseDao runningTestCaseDao) {
        this.runningTestCaseDao = runningTestCaseDao;
    }

    @Override
    public Page<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId) {
        if(orderBy.equals(""))
            orderBy = "test_case_id";
        if(!order.equals("DESC")){
            order="";
        }

        return new Page<>(runningTestCaseDao.getAllByPage(page,size,filter,orderBy,order,projectId),runningTestCaseDao.getSizeOfResultSet(filter,projectId).get());
    }

    @Override
    public void editRunningTestCase(TestCase testCase) {
        runningTestCaseDao.edit(testCase);
    }

    @Override
    public List<UserProfileDto> getWatcherByTestCaseId(int test_case_id) {
        return runningTestCaseDao.getWatcherByTestCaseId(test_case_id);
    }

    @Override
    public List<UserProfileDto> getUsersByName(String name) {

        return runningTestCaseDao.getUsersByName(name);
    }

    @Override
    public void addWatcher(Watcher watcher) {
        runningTestCaseDao.addWatcher(watcher);
    }
}
