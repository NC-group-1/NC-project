package com.nc.project.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
public class Email {
    @Value("${email.from}")
    private String from;
    @Value("${email.subject}")
    private String subject;
    @Value("${email.body}")
    private String body;
    private List<String> recipients;
}
