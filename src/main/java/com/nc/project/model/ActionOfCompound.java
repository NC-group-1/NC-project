package com.nc.project.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionOfCompound {
    private Action action;
    private int orderNum;
    private ParameterKey parameterKey;
}
