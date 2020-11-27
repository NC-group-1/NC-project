package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compound {
    private int actionId;
    private int compoundId;
    private int orderNum;
    private ParameterKey key;
}
