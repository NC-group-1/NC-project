package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScenario {


    @Id
    private int test_scenario_id;
    private String name;
    private String description;
    private int userId;
    private String userName;
    private int projectId;
    private String projectName;
    private ArrayList<Integer> action_compound_id;
    private ArrayList<Action> actions;
    private boolean active = true;

    public TestScenario(int test_scenario_id, String name, String description, int userId, String userName, int projectId, String projectName , boolean active) {
        this.test_scenario_id = test_scenario_id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.userName = userName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.active = active;
    }
}
