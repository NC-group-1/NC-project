package com.nc.project.service.actionInst.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dto.ActionInstDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.DataSet;
import com.nc.project.model.Parameter;
import com.nc.project.model.ParameterKey;
import com.nc.project.service.actionInst.ActionInstService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActionInstServiceImpl implements ActionInstService {
    private final ActionInstDao actionInstDao;
    private final DataSetDao dataSetDao;

    public ActionInstServiceImpl(ActionInstDao actionInstDao, DataSetDao dataSetDao) {
        this.actionInstDao = actionInstDao;
        this.dataSetDao = dataSetDao;
    }

    @Override
    public List<ActionInst> update(ActionInstDto actionInstDto) {
        List<ActionInst> instances = actionInstDto.getActions();
        List<DataSet> datasets = dataSetDao.getByIds(actionInstDto.getDataSetId());

        insertDataSetInActionInstance(instances, datasets);
        instances.forEach(i -> i.setStatus(actionInstDto.getStatus().name()));
        instances.forEach(actionInstDao::update);

        return instances;

    }

    private void insertDataSetInActionInstance(List<ActionInst> actionInsts, List<DataSet> dataSets) {
        for (ActionInst a: actionInsts) {
            for (DataSet ds: dataSets) {
                List<Integer> keyIds = ds.getParameters().stream()
                        .map(Parameter::getKey)
                        .map(ParameterKey::getId)
                        .collect(Collectors.toList());
                if (keyIds.contains(a.getParameterKey())) {
                    a.setDataSet(ds.getId());
                    //a.getDataSet().add(ds.getId());
                }
            }
        }
    }
}
