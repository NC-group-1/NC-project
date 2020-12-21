package com.nc.project.service.mail;

import com.nc.project.dto.TestCaseDetailsDto;

import javax.mail.MessagingException;
import java.util.List;

public interface EmailSendReportService {
    Boolean sendMessageWithAttachment(String to, String subject, TestCaseDetailsDto params, String pathToAttachment)
            throws MessagingException;
}
