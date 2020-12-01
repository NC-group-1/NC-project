package com.nc.project.service.action;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;

import java.util.List;
import java.util.Optional;

public interface ActionService {
    Action createAction(Action action);

    Optional<Action> getActionById(Integer id);

    List<Action> getActionByName(String name);

    Page<Action> getAllActionsByPage(int page, int size);

    Action editAction(Action action);

    void deleteAction(Integer id);

    List<String> getActionTypes();

    Page<Action> getAllActionsByPage(int page, int size, int targetId);
}
