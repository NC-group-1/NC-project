package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private User user;
    private Project project;
    private ArrayList<Integer> action_compound_id;
    private List<TestScenarioComponent> actions;
    private boolean active = true;

    public TestScenario(int test_scenario_id, String name, String description, User user, Project project , boolean active) {
        this.test_scenario_id = test_scenario_id;
        this.name = name;
        this.description = description;
        this.user = user;
        this.project = project;
        this.active = active;
    }

//    public ArrayList<Integer> getAction_compound_id() {
//        if (action_compound_id == null) &&()
//        return action_compound_id;
//    }
}
