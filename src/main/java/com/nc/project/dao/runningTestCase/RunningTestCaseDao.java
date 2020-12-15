package com.nc.project.dao.runningTestCase;

import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;

import java.util.List;
import java.util.Optional;

public interface RunningTestCaseDao {
    List<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    Optional<Integer> getSizeOfResultSet(String filter);
    void edit(TestCase testCase);
    List<UserProfileDto> getWatcherByTestCaseId(int test_case_id);
    List<UserProfileDto> getUsersByName(String name);
    void addWatcher(Watcher watcher);
}