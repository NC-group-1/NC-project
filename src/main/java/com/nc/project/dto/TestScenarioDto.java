package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestScenarioDto {
    private int test_scenario_id;
    private String name;
    private String description;
    private String creatorName;
    private Integer creatorId;
   // private Integer projectId;
    private List<ActionInstDto> actions;

    public TestScenarioDto(int test_scenario_id, String name, String description, String creatorName) {
        this.test_scenario_id = test_scenario_id;
        this.name = name;
        this.description = description;
        this.creatorName = creatorName;
    }
}

