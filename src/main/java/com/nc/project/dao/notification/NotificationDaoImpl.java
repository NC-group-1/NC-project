package com.nc.project.dao.notification;

import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationDaoImpl implements NotificationDao {

    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public NotificationDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<UserNotification> getUserNotifications(Integer userId) {
        String sql = queryService.getQuery("notification.getUserNotifications");
        return jdbcTemplate.query(sql, new Object[]{userId}, new NotificationRowMapper());
    }

    @Override
    public void createNotifications(Integer testCaseId, NotificationType type) {
        String sql = queryService.getQuery("notification.createNotification");
        jdbcTemplate.update(sql, testCaseId, type);
    }

    @Override
    public List<Integer> getTestCaseWatchersId(Integer testCaseId) {
        String sql = queryService.getQuery("notification.getTestCaseWatchersId");
        return jdbcTemplate.query(sql, new Object[]{testCaseId}, (resultSet,i) -> resultSet.getInt("user_id"));
    }

    @Override
    public Optional<TestCaseProgress> getTestCaseProgress(Integer testCaseId) {
        String sql = queryService.getQuery("notification.getTestCaseProgress");
        return jdbcTemplate.query(sql, new Object[]{testCaseId}, (resultSet, i) -> new TestCaseProgress(
                    resultSet.getInt("id"),
                    resultSet.getString("test_case_name"),
                    TestingStatus.valueOf(resultSet.getString("status")),
                    resultSet.getFloat("progress")
        )).stream().findFirst();
    }
}
