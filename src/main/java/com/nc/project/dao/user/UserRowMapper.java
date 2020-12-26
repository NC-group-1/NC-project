package com.nc.project.dao.user;

import com.nc.project.dto.UserProfileDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserProfileDto> {
    @Override
    public UserProfileDto mapRow(ResultSet resultSet, int i) throws SQLException {
        UserProfileDto user = new UserProfileDto(
                resultSet.getInt("user_id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("email"),
                resultSet.getString("role"),
                resultSet.getBoolean("activated"),
                resultSet.getString("image_link"),
                resultSet.getTimestamp("reg_date"),
                resultSet.getString("about_me")
        );

        return user;
    }
}
