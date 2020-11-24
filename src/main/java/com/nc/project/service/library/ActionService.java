package com.nc.project.service.library;

import com.nc.project.model.Action;

import java.util.List;

public interface ActionService {
    Action createAction(Action action);

    Action getActionById(int id);

    Action getActionByKey(String key);

    List<Action> getAllActionsByPage(int page, int size);

    Action editAction(Action action);

    void deleteAction(int id);
}
