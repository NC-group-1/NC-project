package com.nc.project.service.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import java.util.Optional;

public interface DataSetService {

    Page<DataSetGeneralInfoDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    DataSetGeneralInfoDto create(DataSetGeneralInfoDto entity);

    Optional<DataSetGeneralInfoDto> findById(Integer id);

    DataSetGeneralInfoDto update(DataSetGeneralInfoDto entity);

    /**
     * If number of usages = 0, removes dataset and all it parameters.
     *
     * @param id data set id
     * @return number of usages
     */
    int delete(Integer id);
}
