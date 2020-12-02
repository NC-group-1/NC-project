package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScenario {
    private int test_scenario_id;
    private String name;
    private String description;
    private int userId;
    private int projectId;
}
