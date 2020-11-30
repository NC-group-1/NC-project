package com.nc.project.service.mail;


import com.nc.project.model.Email;

import javax.mail.MessagingException;

public interface EmailService {
    Boolean sendSimpleMessage(String to, String subject, String body);
    Boolean sendMessageWithAttachment(String to, String subject, String body) throws MessagingException;
}
