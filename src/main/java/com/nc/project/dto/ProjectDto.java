package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Integer project_id;
    private String name;
    private String link;
    private Timestamp date;
    private String role;
    private Boolean archived;
}
