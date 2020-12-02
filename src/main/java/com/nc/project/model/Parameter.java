package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter {
    private Integer id;
    private ParameterKey key;
    private String value;
}
