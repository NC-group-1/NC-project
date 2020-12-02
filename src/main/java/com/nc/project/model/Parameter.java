package com.nc.project.model;

import com.nc.project.dao.genericDao.GenericDaoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameter implements GenericDaoEntity<Parameter, Integer> {
    private Integer id;
    private ParameterKey key;
    private String value;
    private Integer dataSetId;

    @Override
    public RowMapper<Parameter> returnRowMapper() {
        return (resultSet, i) -> new Parameter(
                resultSet.getObject("parameter_id", Integer.class),
                new ParameterKey(
                        resultSet.getObject("parameter_key_id", Integer.class),
                        resultSet.getString("key")
                ),
                resultSet.getString("value"),
                resultSet.getObject("data_set_id", Integer.class)
        );
    }

    @Override
    public String returnTableName() {
        return "parameter";
    }

    @Override
    public Object[] returnQueryArgs() {
        return new Object[]{value, key.getId(), dataSetId, id};
    }
}
