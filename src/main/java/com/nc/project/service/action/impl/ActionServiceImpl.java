package com.nc.project.service.action.impl;

import com.nc.project.dao.action.ActionDao;
import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.ParameterKey;
import com.nc.project.service.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;

    public ActionServiceImpl(ActionDao actionDao) {
        this.actionDao = actionDao;
    }
    //rewrite
    @Override
    public Action createAction(Action action) {
        if (action.getName() == null) {
            log.info("Action not exists");
            throw new NoSuchElementException("Action not found");
        }
        Action createdAction = actionDao.create(action);
        if (action.getKey() == null) {
            ParameterKey paramKey = new ParameterKey();
            createdAction.setKey(paramKey);
        }

        return createdAction;
    }

    @Override
    public Optional<Action> getActionById(int id) {
        return actionDao.findById(id);
    }

    @Override
    public List<Action> getActionByName(String name) {
        return actionDao.getActionByName(name);
    }

    @Override
    public Page getAllActionsByPage(int page, int size) {
        Optional<Integer> numberOfElements = actionDao.findNumberOfElements();
        Page resultPage = new Page();

        if (numberOfElements.get() > size * page) {
            resultPage.setList(actionDao.findAllActionsByPage(size, size * (page - 1)));
            resultPage.setSize(numberOfElements.get());
        }
        return resultPage;

    }

    @Override
    public Action editAction(Action action) {
        return actionDao.update(action);
    }

    @Override
    public void deleteAction(int id) {
        actionDao.delete(id);
    }
}
