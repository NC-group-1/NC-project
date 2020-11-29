package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSetGeneralInfoDto {
    private Integer id;
    private String name;
    private String description;
    private Integer createdById;
    private String createdByRole;
    private String createdByName;
    private String createdBySurname;
}
