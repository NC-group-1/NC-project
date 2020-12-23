package com.nc.project.service.mail;

public interface EmailService {
    void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment);
}
