package com.nc.project.dto;

import com.nc.project.model.util.ActionType;
import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInstRunDto {
    private Integer id;
    private ActionType actionType;
    private String parameterValue;
    private String parameterKeyKey;
    private TestingStatus status;
    private String result;
    private String actionName;
    private String dataSetName;
    private Integer compoundId;
    private String compoundName;
}
