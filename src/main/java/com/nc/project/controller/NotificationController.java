package com.nc.project.controller;

import com.nc.project.service.notification.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/ncp/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @MessageMapping("/notify")
    public void sendNotification(Integer userId) {
        this.notificationService.sendNotificationsToUser(userId);
    }

    @MessageMapping("/progress")
    public void getProgress(Integer userId) throws InterruptedException {
        this.notificationService.sendUserTestCasesProgress(userId);
    }
    @MessageMapping("/progress/tc")
    public void getTestCaseProgress(Integer testCaseId) throws InterruptedException {
        this.notificationService.sendProgressToTestCase(testCaseId);
    }
}
