package com.nc.project.dao.testCase;

import com.nc.project.dao.GenericDao;
import com.nc.project.model.TestCase;

import java.util.List;
import java.util.Optional;

public interface TestCaseDao extends GenericDao<TestCase, Integer> {
    List<TestCase> getAllByPage(int page, int size, String filter, String orderBy, String order);

    TestCase create(TestCase testCase);
    void edit(TestCase testCase);
    TestCase update(TestCase testCase);
    void delete(Integer ID);
    List<Integer> getTestCasesIdByWatcher(Integer userId);

    Optional<Integer> getSizeOfResultSet(String filter);

    Optional<String> getProjectLinkByTestCaseId(int id);
}
