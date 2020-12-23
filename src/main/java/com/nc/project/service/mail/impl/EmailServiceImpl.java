package com.nc.project.service.mail.impl;

import com.nc.project.service.mail.EmailService;
import com.nc.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final UserService userService;

    public EmailServiceImpl(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
    }

    @Override
    public Boolean sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
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
    public Boolean sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment)
            throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        String text = userService.addLinkToEmail(body, pathToAttachment).orElseThrow();
        mimeMessageHelper.setText("", text);

        boolean isSent = false;
        try {
            javaMailSender.send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            log.error("Sending email with attachment error: {}", e.getMessage());
        }
        return isSent;
    }
}
