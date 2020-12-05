package com.nc.project.dto;

import com.nc.project.model.ActionInst;
import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionInstDto {
    private List<ActionInst> actions;
    private List<Integer> dataSetId;
    private TestingStatus status;
}
