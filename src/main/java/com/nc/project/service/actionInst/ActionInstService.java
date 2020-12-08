package com.nc.project.service.actionInst;

import com.nc.project.dto.ActionInstDto;
import com.nc.project.model.ActionInst;

import java.util.List;

public interface ActionInstService {
    List<ActionInst> update(ActionInstDto actionInstDto);
}
