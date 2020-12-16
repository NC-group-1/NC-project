package com.nc.project.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class TestCaseHistory {
    private Integer id;
    private String name;
    private String role;
    private Timestamp finishDate;
    private String tsName;
    private TestingStatus status;
}
