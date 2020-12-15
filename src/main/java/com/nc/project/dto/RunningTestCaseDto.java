package com.nc.project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunningTestCaseDto {
    private Integer test_case_id;
    private String name;
    private String description;
    private Integer starter_id;
    private Integer watcher_numb;
    private String status;
}