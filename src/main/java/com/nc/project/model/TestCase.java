package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCase {
    @Id
    private Integer id;
    //private Project project_id;
    private Integer creator_id;
    private Integer starter_id;
    //private TestScenario tesc_scenario_id;
    private String name;
    private Timestamp creation_date;
    private Timestamp start_date;
    private Timestamp finish_date;
    private String status;
    private String description;
    //private Interval recurring_time;
    private Integer retries;

    public TestCase(Integer test_case_id, String name, Integer creator_id, Timestamp creation_date, Integer retries) {
        this.id = test_case_id;
        this.name = name;
        this.creator_id = creator_id;
        this.creation_date = creation_date;
        this.retries = retries;
    }


}