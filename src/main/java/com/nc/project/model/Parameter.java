package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nc.project.dao.genericDao.GenericDaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parameter implements GenericDaoEntity<Integer> {
    private Integer id;
    private ParameterKey key;
    private String value;
    private Integer dataSetId;
}
