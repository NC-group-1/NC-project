package com.nc.project.selenium;

import com.nc.project.model.util.ActionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public Optional<String> invoke(ActionType type, String parameter) {
        return this.actionTypeCommandMap.get(type).perform(parameter);
    }
}
