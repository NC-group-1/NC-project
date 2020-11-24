package com.nc.project.dao.compound;

import com.nc.project.model.Compound;
import com.nc.project.model.ParameterKey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompoundRowMapper implements RowMapper<Compound> {

    @Override
    public Compound mapRow(ResultSet resultSet, int i) throws SQLException {
        ParameterKey paramKey = new ParameterKey();
        paramKey.setId(resultSet.getInt("id"));
        paramKey.setKey(resultSet.getString("key"));

        Compound compound = new Compound();
        compound.setActionId(resultSet.getInt("actionId"));
        compound.setCompoundId(resultSet.getInt("compoundId"));
        compound.setOrderNum(resultSet.getInt("orderNum"));
        compound.setKey(paramKey);

        return compound;

    }
}
