package com.nc.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nc.project.model.Project;
import com.nc.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestCaseDetailsDto {
    private Integer id;
    private String name;
    private User creator;
    private User starter;
    private String status;
    private Long numberOfActions;
    private Timestamp startDate;
    private Timestamp finishDate;
    private Project project;
    private List<ActionInstRunDto> actionInstRunDtos;
    private List<UserProfileDto> watchers;
    private Integer testScenarioId;


    public TestCaseDetailsDto(int id, String name, User starter, Timestamp startDate,
                              Timestamp finishDate, Project project, String status) {
        this.id = id;
        this.name = name;
        this.starter = starter;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.project = project;
        this.status = status;

    }
}
