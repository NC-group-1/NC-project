package com.nc.project.dao.action;

import com.nc.project.dao.GenericDao;
import com.nc.project.model.Action;

import java.util.List;

public interface ActionDao extends GenericDao<Action, Integer> {

    List<Action> getActionByName(String name);

    int findNumberOfElements();

    int findNumberOfElements(String filter, String filterTable);

    List<Action> findAllActionsByPage(int limit, int offset);

    Action setActionName(Integer id, String name);

    Action setActionDescription(Integer id, String description);

    List<Action> findAllActionsByPage(int size, int i, int targetId);

    List<Action> findAllActionsByPage(int limit,
                                      int offset,
                                      String filter,
                                      String filterTable,
                                      String orderBy,
                                      String order);
}
