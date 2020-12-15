package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSet {
    private Integer id;
    private String name;
    private User creator;
    private String description;
    private List<Parameter> parameters;

}
