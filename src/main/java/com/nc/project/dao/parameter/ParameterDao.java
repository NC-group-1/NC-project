package com.nc.project.dao.parameter;

import com.nc.project.dao.GenericDao;
import com.nc.project.model.Parameter;
import java.util.List;

public interface ParameterDao extends GenericDao<Parameter, Integer> {
    List<Parameter> getAllByDataSetId (int id);
    int getNumberOfUsages(int id);
}
