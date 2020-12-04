package com.nc.project.service.parameter;

import com.nc.project.model.Parameter;
import java.util.List;
import java.util.Optional;

public interface ParameterService {
    public List<Parameter> getParametersByDataSetId(int id);

    public Parameter create(Parameter entity);

    public Optional<Parameter> findById(Integer id);

    public Parameter update(Parameter entity);

    public void delete(Integer id);
}
