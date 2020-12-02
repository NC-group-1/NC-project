package com.nc.project.dao.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSetGeneralInfoRowMapper implements RowMapper<DataSetGeneralInfoDto> {
    @Override
    public DataSetGeneralInfoDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new DataSetGeneralInfoDto(
                resultSet.getObject("data_set_id", Integer.class),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getObject("user_id", Integer.class),
                resultSet.getString("role"),
                resultSet.getString("username"),
                resultSet.getString("surname")
        );
    }
}
