package com.nc.project.selenium;

import com.nc.project.model.util.TestingStatus;

public interface Executor {
    TestingStatus compareWithActionResult(String parameter, String actionKey);
    TestingStatus compareWithString(String parameter, String actionKey);
    TestingStatus switchTab(String parameter, String actionKey);
    TestingStatus sendKeys(String parameter, String actionKey);
    TestingStatus click(String parameter, String actionKey);
    TestingStatus findElementByCssSelector(String parameter, String actionKey);
    TestingStatus findElementById(String parameter, String actionKey);
    TestingStatus findElementByLinkText(String parameter, String actionKey);
    TestingStatus findElementByPartialLinkText(String parameter, String actionKey);
    TestingStatus findElementByName(String parameter, String actionKey);
    TestingStatus findElementByTagName(String parameter, String actionKey);
    TestingStatus findElementByXpath(String parameter, String actionKey);
    TestingStatus findElementByClassName(String parameter, String actionKey);
    //TODO add other selenium actions
}
