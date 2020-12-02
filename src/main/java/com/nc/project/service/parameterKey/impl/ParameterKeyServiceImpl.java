package com.nc.project.service.parameterKey.impl;

import com.nc.project.dao.parameterKey.ParameterKeyDao;
import com.nc.project.model.ParameterKey;
import com.nc.project.service.parameterKey.ParameterKeyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParameterKeyServiceImpl implements ParameterKeyService {
    private final ParameterKeyDao parameterKeyDao;

    public ParameterKeyServiceImpl(ParameterKeyDao parameterKeyDao) {
        this.parameterKeyDao = parameterKeyDao;
    }

    @Override
    public ParameterKey createParameterKey(ParameterKey parameterKey) {
        return parameterKeyDao.create(parameterKey);
    }

    @Override
    public List<ParameterKey> getKeysByName(String name) {
        return parameterKeyDao.getKeysByName(name);
    }
}
