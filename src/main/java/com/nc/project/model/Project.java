package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private String name;
    private String link;
    private Timestamp date;
    private String role;
    private Boolean archived;
}
