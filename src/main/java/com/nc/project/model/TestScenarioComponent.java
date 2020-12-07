package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScenarioComponent<T extends Action> {
    private T component;
    private int orderNum;
}
