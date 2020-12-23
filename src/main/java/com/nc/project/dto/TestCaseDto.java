package com.nc.project.dto;

import com.nc.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private Integer test_case_id;
    private String name;
    private String description;
    private User user;
    private Integer creator_id;
    private Integer starter_id;
    private Integer test_scenario_id;
    private Timestamp creation_date;
    private Integer iterations_amount;
    private String recurring_time;
    private Timestamp start_date;
    private Timestamp finish_date;
    private String status;
}
