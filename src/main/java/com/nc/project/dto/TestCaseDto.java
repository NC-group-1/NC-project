package com.nc.project.dto;


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
    private Integer creator_id;
    private Timestamp creation_date;
    private Integer iterations_amount;
    private String recurring_time;
    private Timestamp start_date;
    private String status;
}