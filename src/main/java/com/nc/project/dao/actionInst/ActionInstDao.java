package com.nc.project.dao.actionInst;

import com.nc.project.dao.GenericDao;
import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.model.ActionInst;
import java.util.List;
import java.util.Optional;

public interface ActionInstDao extends GenericDao<ActionInst, Integer>{
    List<ActionInstRunDto> getAllActionInstRunDtosByTestCaseId(int id);
    List<ActionInstRunDto> updateAll(List<ActionInstRunDto> actionInstRunDtos);
    List<ActionInst> getAllActionInstancesByTestCaseId(Integer testCaseId);
    Optional<Integer> getNumberOfActionInstancesByTestCaseId(Integer testCaseId);
}
