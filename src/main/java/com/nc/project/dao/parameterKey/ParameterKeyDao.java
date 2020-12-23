package com.nc.project.dao.parameterKey;

import com.nc.project.dao.GenericDao;
import com.nc.project.model.ParameterKey;

import java.util.List;

public interface ParameterKeyDao extends GenericDao<ParameterKey, Integer> {

    List<ParameterKey> getKeysByName(String name);
}
