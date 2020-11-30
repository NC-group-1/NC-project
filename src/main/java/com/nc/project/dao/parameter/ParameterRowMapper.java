package com.nc.project.dao.parameter;

import com.nc.project.model.Parameter;
import com.nc.project.model.ParameterKey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterRowMapper implements RowMapper<Parameter> {
    @Override
    public Parameter mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Parameter(
                resultSet.getObject("parameter_id", Integer.class),
                new ParameterKey(
                        resultSet.getObject("parameter_key_id", Integer.class),
                        resultSet.getString("key")
                ),
                resultSet.getString("value")
        );
    }
}
