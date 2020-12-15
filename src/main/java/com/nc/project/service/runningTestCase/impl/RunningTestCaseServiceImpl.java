package com.nc.project.service.runningTestCase.impl;

import com.nc.project.dao.runningTestCase.RunningTestCaseDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;
import com.nc.project.service.runningTestCase.RunningTestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RunningTestCaseServiceImpl implements RunningTestCaseService {
    @Autowired
    private RunningTestCaseDao runningTestCaseDao;

    @Override
    public Page<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        if(orderBy.equals(""))
            orderBy = "test_case_id";
        if(!order.equals("DESC")){
            order="";
        }

        return new Page(runningTestCaseDao.getAllByPage(page,size,filter,orderBy,order),runningTestCaseDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void editRunningTestCase(TestCase testCase) {
        runningTestCaseDao.edit(testCase);
    }

    @Override
    public Page<UserProfileDto> getWatcherByTestCaseId(int test_case_id) {
        return new Page(runningTestCaseDao.getWatcherByTestCaseId(test_case_id),1);
    }

    @Override
    public Page<UserProfileDto> getUsersByName(String name) {
        return new Page(runningTestCaseDao.getUsersByName(name),1);
    }

    @Override
    public void addWatcher(Watcher watcher) {
        runningTestCaseDao.addWatcher(watcher);
    }
}
