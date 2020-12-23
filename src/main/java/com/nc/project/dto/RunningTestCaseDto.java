package com.nc.project.dto;


import com.nc.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunningTestCaseDto {
    private Integer id;
    private String name;
    private String description;
    private User user;
    private Integer starter_id;
    private Timestamp startDate;
    private Integer watcher_numb;
    private String status;
}