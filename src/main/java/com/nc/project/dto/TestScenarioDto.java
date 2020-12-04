package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestScenarioDto {
    private int test_scenario_id;
    private String name;
    private String description;
    private String role;
}

