package com.nc.project.model;

import com.nc.project.dao.genericDao.GenericDaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter implements GenericDaoEntity<Integer> {
    private Integer id;
    private ParameterKey key;
    private String value;
    private Integer dataSetId;
}
