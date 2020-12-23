package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
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
