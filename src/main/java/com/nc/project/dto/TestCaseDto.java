package com.nc.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGInterval;

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
}