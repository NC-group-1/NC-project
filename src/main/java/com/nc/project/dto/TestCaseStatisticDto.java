package com.nc.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCaseStatisticDto {
    private int failedCount;
    private int passedCount;
}

