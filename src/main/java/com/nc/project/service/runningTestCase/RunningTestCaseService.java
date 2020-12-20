package com.nc.project.service.runningTestCase;

import com.nc.project.dto.Page;
import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;

import java.util.List;


public interface RunningTestCaseService {
    Page<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order, int projectId);
    void editRunningTestCase(TestCase testCase);
}