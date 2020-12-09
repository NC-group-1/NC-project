package com.nc.project.dto;

import com.nc.project.model.Action;
import com.nc.project.model.ParameterKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInstResponseDto {
    private Integer id;
    private Action action;
    private ParameterKey parameterKey;
    private Integer datasetId;
    private Integer orderNum;
}
