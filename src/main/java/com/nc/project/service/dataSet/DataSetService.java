package com.nc.project.service.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import java.util.Optional;

public interface DataSetService {

    public Page<DataSetGeneralInfoDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    public DataSetGeneralInfoDto create(DataSetGeneralInfoDto entity);

    public Optional<DataSetGeneralInfoDto> findById(Integer id);

    public DataSetGeneralInfoDto update(DataSetGeneralInfoDto entity);

    public void delete(Integer id);
}
