package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCase {
    @Id
    private Integer id;
    //private Integer project;
    private Integer creator;
    private Integer starter;
    private Integer testScenario;
    private String name;
    private User user;
    private Timestamp creationDate;
    private Timestamp startDate;
    private Timestamp finishDate;
    private TestingStatus status;
    private String description;
    private String recurringTime;
    private Integer watcherNumb;
    private Integer iterationsAmount;

    public TestCase(Integer id, String name, String description, TestingStatus status) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public TestCase(Integer id, String name, String description, Timestamp creationDate,
                    Integer iterationsAmount, String recurringTime, Timestamp startDate, Integer watcherNumb, TestingStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.iterationsAmount = iterationsAmount;
        this.recurringTime = recurringTime;
        this.startDate = startDate;
        this.watcherNumb = watcherNumb;
        this.status = status;
    }
}
