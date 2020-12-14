package com.nc.project.service.runningTestCase;

import com.nc.project.dto.Page;
import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;


public interface RunningTestCaseService {
    Page<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void editRunningTestCase(TestCase testCase);
    Page<UserProfileDto> getWatcherByTestCaseId(int test_case_id);
    Page<UserProfileDto> getUsersByName(String name);
    void addWatcher(Watcher watcher);
}