package com.nc.project.dao.testCase;

import com.nc.project.dto.TestCaseDto;
import com.nc.project.model.TestCase;

import java.util.List;
import java.util.Optional;

public interface TestCaseDao {
    List<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void edit(TestCase testCase);
    void delete(int test_case_id);
    Optional<Integer> getSizeOfResultSet(String filter);
}