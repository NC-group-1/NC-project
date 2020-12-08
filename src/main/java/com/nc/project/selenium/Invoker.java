package com.nc.project.selenium;

import com.nc.project.model.util.ActionType;
import com.nc.project.model.util.TestingStatus;

import java.util.HashMap;
import java.util.Map;

public class Invoker {

    private Executor executor;
    private Map<ActionType, Command> actionTypeCommandMap;

    public Invoker(Executor executor) {
        if(executor == null) throw new IllegalArgumentException("executor must be not null");
        this.executor = executor;
        this.actionTypeCommandMap = new HashMap<>();
        actionTypeCommandMap.put(ActionType.CLICK,executor::click);
        actionTypeCommandMap.put(ActionType.SEND_KEYS,executor::sendKeys);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_ID,executor::findElementById);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_XPATH,executor::findElementByXpath);
        //TODO map all selenium actions with their action types
    }

    public TestingStatus invoke(ActionType type, String parameter, String actionKey) {
        return this.actionTypeCommandMap.get(type).perform(parameter, actionKey);
    }
}
