package com.nc.project.dao.notification;

import com.nc.project.model.Notification;
import com.nc.project.model.TestCase;
import com.nc.project.model.User;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowMapper implements RowMapper<UserNotification> {
    @Override
    public UserNotification mapRow(ResultSet resultSet, int i) throws SQLException {
        return new UserNotification(
                new Notification(
                        resultSet.getInt("notification_id"),
                        new TestCase(
                                resultSet.getInt("test_case_id"),
                                resultSet.getString("test_case_name"),
                                resultSet.getString("description"),
                                resultSet.getString("status")
                        ),
                        resultSet.getDate("date"),
                        NotificationType.valueOf(resultSet.getString("type"))
                ),
                resultSet.getBoolean("is_read"),
                new User(
                        resultSet.getInt("user_id"),
                        resultSet.getString("email"),
                        resultSet.getString("user_name"),
                        resultSet.getString("surname")
                )
        );
    }
}
