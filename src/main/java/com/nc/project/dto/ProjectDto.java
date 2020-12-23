package com.nc.project.dto;

import com.nc.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private Integer projectId;
    private String name;
    private String link;
    private Timestamp date;
    private User user;
    private Boolean archived;
}
