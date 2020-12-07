package com.nc.project.selenium;

import java.util.Optional;

public interface Executor {
    Optional<String> findElementByCssSelector(String parameter);
    Optional<String> findElementById(String parameter);
    Optional<String> sendKeys(String parameter);
    Optional<String> click(String parameter);
    Optional<String> findElementByLinkText(String parameter);
    Optional<String> findElementByPartialLinkText(String parameter);
    Optional<String> findElementByName(String parameter);
    Optional<String> findElementByTagName(String parameter);
    Optional<String> findElementByXpath(String parameter);
    Optional<String> findElementByClassName(String parameter);
    //TODO add other selenium actions
}
