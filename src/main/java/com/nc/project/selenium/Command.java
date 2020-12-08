package com.nc.project.selenium;

import com.nc.project.model.util.TestingStatus;

@FunctionalInterface
public interface Command {
    TestingStatus perform(String parameter, String actionKey);
}
