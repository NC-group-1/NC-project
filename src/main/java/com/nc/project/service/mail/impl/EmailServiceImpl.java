package com.nc.project.service.mail.impl;

import com.nc.project.model.Email;
import com.nc.project.service.mail.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Boolean sendSimpleMessage(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(String.join(",", email.getRecipients()));
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getBody());
        boolean isSent = false;
        try{
            javaMailSender.send(simpleMailMessage);
            isSent = true;
        } catch (Exception e) {
            log.error("Sending email error: {}", e.getMessage());
        }
        return isSent;
    }

    @Override
    public Boolean sendMessageWithAttachment(Email email, String recoverPasswordLink) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(String.join(",", email.getRecipients()));
//        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessage.setSubject("Password recovery");
//        mimeMessageHelper.setText(email.getBody());
        mimeMessageHelper.setText("Hello, %s!<br/> \n" +
                "             Your new password: <br/>\n" + recoverPasswordLink +
                "             %s<br/><br/>");
        Boolean isSent = false;
        try {
            javaMailSender.send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            log.error("Sending email with attachment error: {}", e.getMessage());
        }
        return isSent;
    }
}
