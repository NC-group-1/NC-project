package com.nc.project.service.runTestCase;

import com.nc.project.dto.ActionInstRunDto;

import java.util.List;

public interface SharedContainerService {
    List<ActionInstRunDto> getFromSharedStorage(Integer testCaseId);
}
