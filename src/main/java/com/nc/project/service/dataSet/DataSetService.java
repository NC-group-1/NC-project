package com.nc.project.service.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.Page;
import com.nc.project.model.Parameter;

import java.util.List;

public interface DataSetService {

    public Page<DataSetGeneralInfoDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    public List<Parameter> getParametersByDataSetId(int id);
}
