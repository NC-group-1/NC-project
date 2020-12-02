package com.nc.project.service.dataSet.impl;

import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import com.nc.project.service.dataSet.DataSetService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DataSetServiceImpl implements DataSetService {

    private final DataSetDao dataSetDao;

    public DataSetServiceImpl(DataSetDao dataSetDao) {
        this.dataSetDao = dataSetDao;
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
    public DataSetGeneralInfoDto create(DataSetGeneralInfoDto entity) {
        return dataSetDao.create(entity);
    }

    @Override
    public Optional<DataSetGeneralInfoDto> findById(Integer id) {
        return dataSetDao.findById(id);
    }

    @Override
    public DataSetGeneralInfoDto update(DataSetGeneralInfoDto entity) {
        return dataSetDao.update(entity);
    }

    @Override
    public void delete(Integer id) {
        dataSetDao.delete(id);
    }
}
