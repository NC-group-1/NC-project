package com.nc.project.service.notification.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.notification.NotificationDao;
import com.nc.project.dto.TestCaseProgress;
import com.nc.project.model.UserNotification;
import com.nc.project.model.util.NotificationType;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.notification.NotificationService;
import com.nc.project.service.runTestCase.RunTestCaseService;
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
    private final ActionInstDao actionInstDao;
    private final RunTestCaseService runTestCaseService;

    public NotificationServiceImpl(NotificationDao notificationDao,
                                   SimpMessagingTemplate messagingTemplate,
                                   TestCaseService testCaseService,
                                   ActionInstDao actionInstDao,
                                   RunTestCaseService runTestCaseService) {
        this.notificationDao = notificationDao;
        this.messagingTemplate = messagingTemplate;
        this.testCaseService = testCaseService;
        this.actionInstDao = actionInstDao;
        this.runTestCaseService = runTestCaseService;
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
    public void sendProgressToTestCase(Integer testCaseId) {
        Optional<TestCaseProgress> progress = this.getTestCaseProgress(testCaseId);
        progress.ifPresent(testCaseProgress -> {
            if(testCaseProgress.getStatus() != TestingStatus.CANCELED
                    && testCaseProgress.getStatus() != TestingStatus.FAILED
                    && testCaseProgress.getStatus() != TestingStatus.PASSED
                    && testCaseProgress.getStatus() != TestingStatus.SCHEDULED) {
                float completed = runTestCaseService.getActionInstRunDtosFromSharedStorage(testCaseId).size();
                int all = actionInstDao.getNumberOfActionInstancesByTestCaseId(testCaseId).orElse(1);
                testCaseProgress.setProgress(completed/all);
            }
            messagingTemplate.convertAndSend("/topic/progress/" + testCaseId, testCaseProgress);
        });
    }

    @Override
    public void sendProgressToTestCase(TestCaseProgress testCaseProgress) {
        messagingTemplate.convertAndSend("/topic/progress/" + testCaseProgress.getTestCaseId(),
                testCaseProgress);
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

    @Override
    public Boolean changeRead(Integer userId, Integer notificationId) {
        return this.notificationDao.changeRead(userId, notificationId);
    }

    @Override
    public Boolean deleteNotification(Integer userId, Integer notificationId) {
        return this.notificationDao.deleteNotification(userId, notificationId);
    }


}
