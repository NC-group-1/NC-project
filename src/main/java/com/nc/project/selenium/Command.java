package com.nc.project.selenium;

import java.util.Optional;

@FunctionalInterface
public interface Command {
    Optional<String> perform(String parameter);
}
