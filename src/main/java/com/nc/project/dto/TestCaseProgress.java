package com.nc.project.dto;

import com.nc.project.model.util.TestingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCaseProgress {
    private Integer testCaseId;
    private String name;
    private TestingStatus status;
    private Float progress;
}
