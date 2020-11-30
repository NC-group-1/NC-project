package com.nc.project.dao.compound;

import com.nc.project.model.Compound;
import com.nc.project.model.util.ActionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompoundRowMapper implements RowMapper<Compound> {

    @Override
    public Compound mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Compound(
                resultSet.getInt("action_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                ActionType.Compound
        );
    }
}
