package com.nc.project.service.testCase;

import com.nc.project.dto.Page;
import com.nc.project.model.TestCase;
import com.nc.project.dto.TestCaseDto;

public interface TestCaseService {
    Page<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void editTestCase(TestCase testCase);
    void deleteTestCase(int test_case_id);
}