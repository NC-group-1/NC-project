package com.nc.project.service.mail.impl;

import com.nc.project.dto.ActionInstDto;
import com.nc.project.dto.TestCaseDetailsDto;
import com.nc.project.service.mail.EmailSendReportService;
import com.nc.project.service.project.ProjectService;
import com.nc.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@Slf4j
public class EmailSendReportServiceImpl implements EmailSendReportService {
    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;
    private final ProjectService projectService;

    public EmailSendReportServiceImpl(JavaMailSender javaMailSender, ProjectService projectService) {
        this.javaMailSender = javaMailSender;
        this.projectService = projectService;
    }

    @Override
    public Boolean sendMessageWithAttachment(String to, String subject, TestCaseDetailsDto params, String pathToAttachment)
            throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        String text = projectService.addParamsToEmail(params, pathToAttachment).orElseThrow();
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
