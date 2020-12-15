package com.nc.project.dto;

import com.nc.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestScenarioDto {
    private int testScenarioId;
    private String name;
    private String description;
    private User user;
    // private Integer projectId;
    private List<ActionInstDto> actions;

    public TestScenarioDto(int testScenarioId, String name, String description) {
        this.testScenarioId = testScenarioId;
        this.name = name;
        this.description = description;
    }

    public TestScenarioDto(int testScenarioId, String name, String description, User user) {
        this.testScenarioId = testScenarioId;
        this.name = name;
        this.description = description;
        this.user = user;
    }
}

