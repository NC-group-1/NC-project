package com.nc.project.service.mail;

import javax.mail.MessagingException;

public interface EmailService {
    Boolean sendSimpleMessage(String to, String subject, String body);
    Boolean sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment)
            throws MessagingException;
}
