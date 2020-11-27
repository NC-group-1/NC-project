package com.nc.project.service.parameterKey;

import com.nc.project.model.ParameterKey;

import java.util.List;

public interface ParameterKeyService {
    ParameterKey createParameterKey(ParameterKey parameterKey);

    List<ParameterKey> getKeysByName(String name);
}
