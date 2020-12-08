package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionInst {
    private Integer id;
    private Integer action;
    private Integer compound;
    private Integer testCase;
    private Integer dataSet;
    private Integer parameterKey;
    private String status;
    private Integer orderNum;

}
