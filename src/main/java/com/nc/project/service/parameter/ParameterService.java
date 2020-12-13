package com.nc.project.service.parameter;

import com.nc.project.model.Parameter;
import java.util.List;
import java.util.Optional;

public interface ParameterService {
    List<Parameter> getParametersByDataSetId(int id);

    Parameter create(Parameter entity);

    Optional<Parameter> findById(Integer id);

    Optional<Parameter> update(Parameter entity);

    /**
     * If number of usages = 0, removes dataset and all it parameters.
     *
     * @param id data set id
     * @return number of usages
     */
    int delete(Integer id);

    int getNumberOfUsages(Integer id);
}
