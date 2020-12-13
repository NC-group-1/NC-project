package com.nc.project.dao.notification;

import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;

import java.util.List;
import java.util.Optional;

public interface NotificationDao {
    List<UserNotification> getUserNotifications(Integer userId);
    void createNotifications(Integer testCaseId, NotificationType type);
    List<Integer> getTestCaseWatchersId(Integer testCaseId);
    Optional<TestCaseProgress> getTestCaseProgress(Integer testCaseId);

}
