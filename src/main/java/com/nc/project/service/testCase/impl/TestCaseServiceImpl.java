package com.nc.project.service.testCase.impl;

import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.model.TestCase;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestCaseServiceImpl implements TestCaseService {
    @Autowired
    private TestCaseDao testCaseDao;

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
    public void editTestCase(TestCase testCase) {
        testCaseDao.edit(testCase);
    }

    @Override
    public void deleteTestCase(int id) {
        testCaseDao.delete(id);
    }
}