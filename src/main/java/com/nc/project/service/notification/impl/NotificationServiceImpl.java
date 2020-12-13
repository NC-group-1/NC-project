package com.nc.project.service.notification.impl;

import com.nc.project.dao.notification.NotificationDao;
import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;
import com.nc.project.service.notification.NotificationService;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDao notificationDao;
    private final SimpMessagingTemplate messagingTemplate;
    private final TestCaseService testCaseService;

    public NotificationServiceImpl(NotificationDao notificationDao,
                                   SimpMessagingTemplate messagingTemplate,
                                   TestCaseService testCaseService) {
        this.notificationDao = notificationDao;
        this.messagingTemplate = messagingTemplate;
        this.testCaseService = testCaseService;
    }


    @Override
    public void createNotification(Integer testCaseId, NotificationType type) {
        this.notificationDao.createNotifications(testCaseId, type);
        sendNotificationsToUsers(this.notificationDao.getTestCaseWatchersId(testCaseId));
    }

    @Override
    public Optional<TestCaseProgress> getTestCaseProgress(Integer testCaseId) {
        return notificationDao.getTestCaseProgress(testCaseId);
    }
    @Override
    public List<UserNotification> getUserNotifications(Integer userId) {
        return notificationDao.getUserNotifications(userId);
    }


    @Override
    public void sendProgressToTestCase(Integer testCaseId) throws InterruptedException {
        Optional<TestCaseProgress> progress = this.getTestCaseProgress(testCaseId);
        progress.ifPresent(testCaseProgress -> messagingTemplate.convertAndSend("/topic/progress/" + testCaseId, testCaseProgress));

    }
    @Override
    public void sendNotificationsToUser(Integer userId) {
        List<UserNotification> userNotifications = this.getUserNotifications(userId);
        messagingTemplate.convertAndSend("/topic/notification/" + userId, userNotifications);
    }


    @Override
    public void sendUserTestCasesProgress(Integer userId) throws InterruptedException {
        for (Integer id : testCaseService.getTestCasesIdByWatcher(userId)) {
            sendProgressToTestCase(id);
        }
    }
    @Override
    public void sendNotificationsToUsers(List<Integer> userIds) {
        for (Integer id : userIds) {
            sendNotificationsToUser(id);
        }
    }

}
