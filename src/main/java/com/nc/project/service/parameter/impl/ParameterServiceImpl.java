package com.nc.project.service.parameter.impl;

import com.nc.project.dao.parameter.ParameterDao;
import com.nc.project.dao.parameterKey.ParameterKeyDao;
import com.nc.project.model.Parameter;
import com.nc.project.service.parameter.ParameterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParameterServiceImpl implements ParameterService {

    private final ParameterDao parameterDao;
    private final ParameterKeyDao parameterKeyDao;

    public ParameterServiceImpl(ParameterDao parameterDao, ParameterKeyDao parameterKeyDao) {
        this.parameterDao = parameterDao;
        this.parameterKeyDao = parameterKeyDao;
    }

    @Override
    public List<Parameter> getParametersByDataSetId(int id) {
        return parameterDao.getAllByDataSetId(id);
    }

    @Override
    @Transactional
    public Parameter create(Parameter entity) {
        entity.setKey(parameterKeyDao.create(entity.getKey()));
        return parameterDao.create(entity);
    }

    @Override
    public Optional<Parameter> findById(Integer id) {
        return parameterDao.findById(id);
    }

    @Override
    public Optional<Parameter> update(Parameter entity) {
        if(parameterDao.getNumberOfUsages(entity.getId()) == 0) {
            return Optional.of(parameterDao.update(entity));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int delete(Integer id) {
        int numberOfUsages = parameterDao.getNumberOfUsages(id);
        if(numberOfUsages == 0) {
            parameterDao.delete(id);
        }
        return numberOfUsages;
    }

    @Override
    public int getNumberOfUsages(Integer id) {
        return parameterDao.getNumberOfUsages(id);
    }
}
