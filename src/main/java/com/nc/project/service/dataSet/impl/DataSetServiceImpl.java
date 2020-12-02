package com.nc.project.service.dataSet.impl;

import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dao.parameter.ParameterDao;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import com.nc.project.model.Parameter;
import com.nc.project.service.dataSet.DataSetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataSetServiceImpl implements DataSetService {

    private final DataSetDao dataSetDao;
    private final ParameterDao parameterDao;

    public DataSetServiceImpl(DataSetDao dataSetDao, ParameterDao parameterDao) {
        this.dataSetDao = dataSetDao;
        this.parameterDao = parameterDao;
    }

    @Override
    public Page<DataSetGeneralInfoDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        int numberOfElements = dataSetDao.getSizeOfResultSet(filter);
        Page<DataSetGeneralInfoDto> resultPage = new Page<>();
        if (numberOfElements > size * page) {
            resultPage.setList(dataSetDao.getGeneralInfoByPage(size, size * page, filter, orderBy, order));
            resultPage.setSize(numberOfElements);
        }
        return resultPage;
    }

    @Override
    public List<Parameter> getParametersByDataSetId(int id) {
        return parameterDao.getAllByDataSetId(id);
    }
}
