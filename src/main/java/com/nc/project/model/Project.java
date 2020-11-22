package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private int id;
    private String name;
    private String link;
    private Timestamp creationDate;
    private User user;
    private Boolean archived;
}
