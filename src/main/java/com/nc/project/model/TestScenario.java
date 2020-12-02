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
    private int projectId;
    private ArrayList<Integer> action_compound_id;
    private boolean active = true;
}
