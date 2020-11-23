package com.nc.project.service.mail;


import com.nc.project.model.Email;

import javax.mail.MessagingException;

public interface EmailService {
    Boolean sendSimpleMessage(Email email);
    Boolean sendMessageWithAttachment(Email email, String recoverPasswordLink) throws MessagingException;
}
