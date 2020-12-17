package com.nc.project.selenium;

import java.util.Optional;

public interface Context {
    void put(Integer actionId, String value);
    Optional<String> get(String actionKey);
}
