package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Integer project_id;
    private String name;
    private String link;
    private Timestamp date;
    private String role;
    private Integer user_id;
    private Boolean archived;

    public Project(Integer project_id, String name, String link, Timestamp date, String role, Boolean archived) {
        this.project_id = project_id;
        this.name = name;
        this.link = link;
        this.date = date;
        this.role = role;
        this.archived = archived;
    }
    public Project(Integer project_id, String name, String link,Integer user_id) {
        this.project_id = project_id;
        this.name = name;
        this.link = link;
        this.user_id = user_id;
    }

    public Project(Integer project_id, String name, String link, Timestamp date) {
        this.project_id = project_id;
        this.name = name;
        this.link = link;
        this.date = date;
    }
}
