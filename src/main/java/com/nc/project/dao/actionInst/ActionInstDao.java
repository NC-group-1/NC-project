package com.nc.project.dao.actionInst;

import com.nc.project.dao.GenericDao;
import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.model.ActionInst;
import java.util.List;

public interface ActionInstDao extends GenericDao<ActionInst, Integer>{
    List<ActionInstRunDto> getAllByTestCaseId(int id);
}
