package com.nc.project.dao.dataSet;

import com.nc.project.model.DataSet;
import com.nc.project.model.Parameter;
import com.nc.project.model.ParameterKey;
import com.nc.project.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DataSetRowMapper implements RowMapper<DataSet> {

    @Override
    public DataSet mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));

        ParameterKey parameterKey = new ParameterKey();
        parameterKey.setKey(resultSet.getString("key"));

        Parameter parameter = new Parameter();
        parameter.setId(resultSet.getInt("parameter_id"));
        parameter.setParameterKey(parameterKey);
        parameter.setValue(resultSet.getString("value"));
        parameter.setDataSetId(resultSet.getInt("data_set_id"));

        DataSet dataSet = new DataSet();
        dataSet.setId(resultSet.getInt("data_set_id"));
        dataSet.setName(resultSet.getString("name"));
        dataSet.setCreator(user);
        dataSet.setDescription(resultSet.getString("description"));
        dataSet.setParameters(Arrays.asList(parameter));

        return dataSet;
    }
}
