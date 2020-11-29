package com.nc.project.dao.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.model.DataSet;

import java.util.List;
import java.util.Optional;

public class DataSetDaoImpl implements DataSetDao {

    @Override
    public List<DataSetGeneralInfoDto> getGeneralInfoByPage(int page, int size, String filter, String orderBy, String order) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public DataSet create(DataSet entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<DataSet> findById(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public DataSet update(DataSet entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
