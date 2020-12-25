package com.nc.project.service.notification;

import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    List<UserNotification> getUserNotifications(Integer userId);
    void createNotification(Integer testCaseId, NotificationType type);

    Optional<TestCaseProgress> getTestCaseProgress(Integer testCaseId);

    void sendUserTestCasesProgress(Integer userId) throws InterruptedException;
    void sendProgressToTestCase(Integer testCaseId) throws InterruptedException;
    void sendProgressToTestCase(TestCaseProgress testCaseProgress);
    void sendNotificationsToUser(Integer userId);
    void sendNotificationsToUsers(List<Integer> userIds);
    Boolean changeRead(Integer userId, Integer notificationId);

    Boolean deleteNotification(Integer userId, Integer notificationId);
    void sendActionInstToTestCaseSocket(List<ActionInstRunDto> actionInstRunDtos, Integer testCaseId);
    void sendActionInstToTestCaseSocket(Integer testCaseId);
}
