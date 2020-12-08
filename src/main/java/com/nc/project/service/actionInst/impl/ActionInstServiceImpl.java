package com.nc.project.service.actionInst.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.dataSet.DataSetDao;
import com.nc.project.dto.ActionInstDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.Parameter;
import com.nc.project.model.ParameterKey;
import com.nc.project.model.util.TestingStatus;
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
    public List<ActionInst> update(List<ActionInstDto> actionInstDtos) {
        for (ActionInstDto a: actionInstDtos) {
            ActionInst action = a.getAction();
            action.setStatus(a.getStatus().name());

            List<Integer> keyIds = a.getDataSet().getParameters().stream()
                    .map(Parameter::getKey)
                    .map(ParameterKey::getId)
                    .collect(Collectors.toList());

            if (keyIds.contains(a.getAction().getParameterKey())) {
                action.setDataSet(a.getDataSet().getId());
            }

        }

        List<ActionInst> instances = actionInstDtos.stream().map(ActionInstDto::getAction)
                .collect(Collectors.toList());

        instances.forEach(actionInstDao::update);

        return instances;

    }

}
