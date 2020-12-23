package com.nc.project.selenium;

import com.nc.project.model.util.ActionType;
import com.nc.project.model.util.TestingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Invoker {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Executor executor;
    private final Map<ActionType, BiFunction<String, Integer, TestingStatus>> actionTypeCommandMap;

    public Invoker(Executor executor) {
        if(executor == null) throw new IllegalArgumentException("executor must be not null");
        this.executor = executor;
        this.actionTypeCommandMap = new HashMap<>();
        actionTypeCommandMap.put(ActionType.CLICK,executor::click);
        actionTypeCommandMap.put(ActionType.SEND_KEYS,executor::sendKeys);
        actionTypeCommandMap.put(ActionType.SWITCH_TAB,executor::switchTab);
        actionTypeCommandMap.put(ActionType.COMPARE_WITH_STRING,executor::compareWithString);
        actionTypeCommandMap.put(ActionType.COMPARE_WITH_CONTEXT_VALUE,executor::compareWithContextValue);
        actionTypeCommandMap.put(ActionType.SCROLL_PAGE_TO_END,executor::scrollPageToEnd);
        actionTypeCommandMap.put(ActionType.SAVE_ELEMENT_TEXT_TO_CONTEXT,executor::saveElementTextToContext);
        actionTypeCommandMap.put(ActionType.SAVE_ELEMENT_ATTRIBUTE_TO_CONTEXT,executor::saveElementAttributeToContext);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_ID,executor::findElementById);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_XPATH,executor::findElementByXpath);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_CLASS_NAME,executor::findElementByClassName);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_CSS_SELECTOR,executor::findElementByCssSelector);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_LINK_TEXT,executor::findElementByLinkText);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_NAME,executor::findElementByName);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_PARTIAL_LINK_TEXT,executor::findElementByPartialLinkText);
        actionTypeCommandMap.put(ActionType.FIND_ELEMENT_BY_TAG_NAME,executor::findElementByTagName);
        //TODO map all selenium actions with their action types
    }

    public TestingStatus invoke(ActionType type, String parameter, Integer actionId) {
        try {
            return this.actionTypeCommandMap.get(type).apply(parameter, actionId);
        } catch (Exception e) {
            log.error("Error in action "+type.name()+" "+e.getMessage());
            if(e.getMessage() != null){
                executor.addToContext(actionId, "Error in action "+type.name()
                        +" "+e.getMessage().split("\n")[0]);
            } else {
                executor.addToContext(actionId, "Error in action "+type.name());
            }
            return TestingStatus.FAILED;
        }
    }
}
