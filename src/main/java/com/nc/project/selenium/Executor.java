package com.nc.project.selenium;

import com.nc.project.model.util.TestingStatus;

public interface Executor {
    void addToContext(Integer actionId, String value);
    TestingStatus saveElementAttributeToContext(String parameter, Integer actionId);
    TestingStatus saveElementTextToContext(String parameter, Integer actionId);
    TestingStatus scrollPageToEnd(String parameter, Integer actionId);
    TestingStatus compareWithContextValue(String parameter, Integer actionId);
    TestingStatus compareWithString(String parameter, Integer actionId);
    TestingStatus switchTab(String parameter, Integer actionId);
    TestingStatus sendKeys(String parameter, Integer actionId);
    TestingStatus click(String parameter, Integer actionId);
    TestingStatus findElementByCssSelector(String parameter, Integer actionId);
    TestingStatus findElementById(String parameter, Integer actionId);
    TestingStatus findElementByLinkText(String parameter, Integer actionId);
    TestingStatus findElementByPartialLinkText(String parameter, Integer actionId);
    TestingStatus findElementByName(String parameter, Integer actionId);
    TestingStatus findElementByTagName(String parameter, Integer actionId);
    TestingStatus findElementByXpath(String parameter, Integer actionId);
    TestingStatus findElementByClassName(String parameter, Integer actionId);
    //TODO add other selenium actions
}
