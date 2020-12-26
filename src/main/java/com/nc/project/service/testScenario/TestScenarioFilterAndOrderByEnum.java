package com.nc.project.service.testScenario;

public enum TestScenarioFilterAndOrderByEnum {
    NAME("t.name"),
    CREATORNAME("(CASE WHEN concat(u.name, ' ', surname) = ' ' THEN email ELSE concat(u.name, ' ', surname) END)"),
    DESCRIPTION("description");

    private final String value;

    TestScenarioFilterAndOrderByEnum(String value) {
        this.value = value;
    }

    public static TestScenarioFilterAndOrderByEnum getEnum(String value) {
        for (TestScenarioFilterAndOrderByEnum v : values()) {
            if (v.name().equals(value.toUpperCase())) {
                return v;
            }
        }
        return NAME;
    }

    public String getValue() {
        return value;
    }
}
