package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGInterval;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCase {
    private Integer id;
    //private Integer project;
    private Integer creator;
    private Integer starter;
    private Integer testScenario;
    private String name;
    private Timestamp creationDate;
    private Timestamp startDate;
    private Timestamp finishDate;
    private String status;
    private String description;
    private PGInterval recurringTime;
    private Integer iterationsAmount;
}
