package com.nc.project.model;

import lombok.Data;

import java.util.List;

@Data
public class Email {
    private String subject;
    private String body;
    private List<String> recipients;
}
