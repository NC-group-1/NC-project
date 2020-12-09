package com.nc.project.dto;

import com.nc.project.model.Action;
import com.nc.project.model.DataSet;
import com.nc.project.model.ParameterKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInstDto {
    private Action action;
    private Integer datasetId;
    private Integer orderNum;
    private ParameterKey parameterKey;

}
