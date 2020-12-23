package com.nc.project.dao.action;

import com.nc.project.model.Action;
import com.nc.project.model.ParameterKey;
import com.nc.project.model.util.ActionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionRowMapper implements RowMapper<Action> {

    @Override
    public Action mapRow(ResultSet resultSet, int i) throws SQLException {
        ParameterKey paramKey = new ParameterKey();
        paramKey.setId(resultSet.getObject("parameter_key_id", Integer.class));
        paramKey.setKey(resultSet.getString("key"));

        Action action = new Action();
        action.setId(resultSet.getInt("action_id"));
        action.setName(resultSet.getString("name"));
        action.setDescription(resultSet.getString("description"));
        action.setType(ActionType.valueOf(resultSet.getString("type")));
        action.setParameterKey(paramKey);

        return action;
    }
}
