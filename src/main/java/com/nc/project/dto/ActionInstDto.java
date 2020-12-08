package com.nc.project.dto;

import com.nc.project.model.ActionInst;
import com.nc.project.model.DataSet;
import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInstDto {
    private ActionInst action;
    private DataSet dataSet;
    private TestingStatus status;
}
