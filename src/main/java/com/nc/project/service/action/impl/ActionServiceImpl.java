package com.nc.project.service.action.impl;

import com.nc.project.dao.action.ActionDao;
import com.nc.project.dao.parameterKey.ParameterKeyDao;
import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.ParameterKey;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.action.ActionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;
    private final ParameterKeyDao parameterKeyDao;

    private final List<String> actionTypes = Arrays.stream(ActionType.values())
            .filter(actionType -> !actionType.equals(ActionType.COMPOUND))
            .map(Enum::name)
            .collect(Collectors.toList());

    public ActionServiceImpl(ActionDao actionDao, ParameterKeyDao parameterKeyDao) {
        this.actionDao = actionDao;
        this.parameterKeyDao = parameterKeyDao;
    }

    @Override
    public Action createAction(Action action) {
        if (action.getName() == null) {
            log.info("Action not exists");
            throw new NoSuchElementException("Action not found");
        }

        ParameterKey key = action.getKey();

        if (key != null) {
            key = parameterKeyDao.create(key);
            action.setKey(key);
        }

        return actionDao.create(action);

    }

    @Override
    public Optional<Action> getActionById(Integer id) {
        return actionDao.findById(id);
    }

    @Override
    public List<Action> getActionByName(String name) {
        return actionDao.getActionByName(name);
    }

    /*
    public Page getAllActionsByPage(int page, int size) {
        int numberOfElements = actionDao.findNumberOfElements();
        Page resultPage = new Page();

        if (numberOfElements > size * page) {
            resultPage.setList(actionDao.findAllActionsByPage(size, size * page));
            resultPage.setSize(numberOfElements);
        }
        return resultPage;

    }
     */

    @Override
    public Action editAction(Action action) {
        System.out.println(action);
        ParameterKey key = action.getKey();
        if (key != null) {
            key = parameterKeyDao.create(key);
            action.setKey(key);
        }
        return actionDao.update(action);
    }

    @Override
    public void deleteAction(Integer id) {
        actionDao.delete(id);
    }

    @Override
    public List<String> getActionTypes() {
        return actionTypes;
    }

    @Override
    public Page<Action> getAllActionsByPage(int page, int size, int targetId) {
        int numberOfElements = actionDao.findNumberOfElements();
        Page<Action> resultPage = new Page<>();

        if (numberOfElements > size * page) {
            resultPage.setList(actionDao.findAllActionsByPage(size, size * page, targetId));
            resultPage.setSize(numberOfElements);
        }
        return resultPage;
    }

    @Override
    public Page<Action> getAllActionsByPage(int page,
                                            int size,
                                            String filter,
                                            String filterTable,
                                            String orderBy,
                                            String order) {
        int numberOfElements = actionDao.findNumberOfElements(filter, filterTable);
        Page<Action> resultPage = new Page<>();
        if (numberOfElements > size * page) {
            resultPage.setList(actionDao.findAllActionsByPage(size, size * page,
                    filter,filterTable,orderBy,order));
            resultPage.setSize(numberOfElements);
        }
        return resultPage;
    }
}
