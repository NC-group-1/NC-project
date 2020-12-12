package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Integer projectId;
    private String name;
    private String link;
    private Timestamp date;
    private User user;
    private Boolean archived;


    public Project(Integer projectId, String name, String link, Timestamp date) {
        this.projectId = projectId;
        this.name = name;
        this.link = link;
        this.date = date;
    }

    public Project(Integer projectId) {
        this.projectId = projectId;
    }
}
