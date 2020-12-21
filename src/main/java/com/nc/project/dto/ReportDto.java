package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private TestCaseDetailsDto testCaseDetailsDto;
    private String email;
}
