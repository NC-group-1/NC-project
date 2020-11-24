package com.nc.project.service.library.impl;

import com.nc.project.dao.action.ActionDao;
import com.nc.project.model.Action;
import com.nc.project.service.library.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        if (action == null) {
            log.info("Action not exists");
            throw new NoSuchElementException("Action not found");
        }
        Action createdAction = actionDao.createAction(action);

        return createdAction;
    }

    @Override
    public Action getActionById(int id) {
        return actionDao.findActionById(id);
    }

    @Override
    public Action getActionByKey(String key) {
        return actionDao.getActionByKey(key);
    }

    @Override
    public List<Action> getAllActionsByPage(int page, int size) {
        int number = size * (page - 1);
        return actionDao.findAllActionsByPage(size, number);
    }

    @Override
    public Action editAction(Action action) {
        return actionDao.editAction(action);
    }

    @Override
    public void deleteAction(int id) {
        actionDao.removeAction(id);
    }
}
