package com.nc.project.dao.action;

import com.nc.project.dao.GenericDao;
import com.nc.project.model.Action;

import java.util.List;
import java.util.Optional;

public interface ActionDao extends GenericDao<Action, Integer> {

    List<Action> getActionByName(String name);

    Optional<Integer> findNumberOfElements();

    List<Action> findAllActionsByPage(int limit, int offset);

    Action setActionName(int id, String name);

    Action setActionDescription(int id, String description);

}
