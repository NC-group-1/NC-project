package com.nc.project.dao.action;

import com.nc.project.model.Action;

import java.util.List;

public interface ActionDao {

    Action createAction(Action action);

    Action getActionByKey(String key);

    List<Action> findAllActionsByPage(int size, int number);

    Action editAction(Action action);

    Action findActionById(int id);

    void removeAction(int id);

}
