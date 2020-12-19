package com.nc.project.dao.testCase;

import com.nc.project.dao.GenericDao;
import com.nc.project.dto.TestCaseDetailsDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.dto.TestCaseHistory;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.TestCase;
import com.nc.project.model.Watcher;

import java.util.List;
import java.util.Optional;

public interface TestCaseDao extends GenericDao<TestCase, Integer> {
    List<TestCase> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId);

    TestCase create(TestCase testCase);

    //void edit(TestCase testCase);

    TestCase editForRun(TestCase testCase);

    void delete(Integer ID);

    List<UserProfileDto> getListWatcherByTestCaseId(int test_case_id);

    List<UserProfileDto> getUsersByName(String name);

    void addWatcher(Watcher watcher);

    List<Integer> getTestCasesIdByWatcher(Integer userId);

    Optional<Integer> getSizeOfResultSet(String filter, int projectId);

    Optional<String> getProjectLinkByTestCaseId(int id);

    Optional<TestCaseDetailsDto> getTestCaseDetailsById(Integer id);

    Boolean editTestCaseActions(TestScenarioDto testScenarioDto);

    List<TestCaseHistory> getHistory(int pageIndex, int pageSize, String filter, String orderBy, String order, int projectId);

    Integer getSizeOfHistoryResultSet(String filter, int projectId);
}
