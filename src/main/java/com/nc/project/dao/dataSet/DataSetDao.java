package com.nc.project.dao.dataSet;

import com.nc.project.dao.GenericDao;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.model.DataSet;

import java.util.List;

public interface DataSetDao extends GenericDao<DataSetGeneralInfoDto,Integer> {

    List<DataSetGeneralInfoDto> getGeneralInfoByPage(int limit,
                                                     int offset,
                                                     String filter,
                                                     String orderBy,
                                                     String order);

    public int getSizeOfResultSet(String filter);

    //List<DataSet> getByIds(List<Integer> dataSetIds);

}
