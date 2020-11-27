package com.nc.project.dao.parameterKey;

import com.nc.project.model.ParameterKey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParameterKeyRowMapper implements RowMapper<ParameterKey> {
    @Override
    public ParameterKey mapRow(ResultSet resultSet, int i) throws SQLException {
        ParameterKey paramKey = new ParameterKey();
        paramKey.setId(resultSet.getInt("id"));
        paramKey.setKey(resultSet.getString("key"));

        return paramKey;
    }
}
