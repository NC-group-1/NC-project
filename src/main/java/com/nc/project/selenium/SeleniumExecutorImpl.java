package com.nc.project.selenium;

import com.nc.project.model.util.TestingStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;
import java.util.Set;

public class SeleniumExecutorImpl implements Executor{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final WebDriver driver;
    private final Context context;
    private WebElement currentWebElement;
    private String currentContextValue;

    public SeleniumExecutorImpl(WebDriver driver, Context context) {
        this.driver = driver;
        this.context = context;
    }

    //TODO implement other selenium actions

    @Override
    public void addToContext(Integer actionId, String value) {
        context.put(actionId, value);
    }

    @Override
    public TestingStatus saveElementAttributeToContext(String parameter, Integer actionId) {
        currentContextValue = currentWebElement.getAttribute(parameter);
        context.put(actionId, currentContextValue);
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus saveElementTextToContext(String parameter, Integer actionId) {
        currentContextValue = currentWebElement.getText();
        context.put(actionId, currentContextValue);
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus scrollPageToEnd(String parameter, Integer actionId) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus compareWithContextValue(String parameter, Integer actionId) {
        Optional<String> expected = context.get(actionId);
        if(currentContextValue.equals(expected.orElse(null))){
            return TestingStatus.PASSED;
        } else {
            context.put(actionId,"Comparing mismatch! Got("+currentContextValue+") Expected("+expected+")");
            return TestingStatus.FAILED;
        }
    }

    @Override
    public TestingStatus compareWithString(String parameter, Integer actionId) {
        if(currentContextValue.equals(parameter)){
            return TestingStatus.PASSED;
        } else {
            context.put(actionId,"Comparing mismatch! Got("+currentContextValue+") Expected("+parameter+")");
            return TestingStatus.FAILED;
        }
    }

    @Override
    public TestingStatus switchTab(String parameter, Integer actionId) {
        Set<String> handles = driver.getWindowHandles();
        handles.remove(driver.getWindowHandle());
        if(!handles.isEmpty()){
            driver.switchTo().window(handles.iterator().next());
            return TestingStatus.PASSED;
        } else {
            context.put(actionId, "No tab to switch");
            return TestingStatus.FAILED;
        }
    }

    @Override
    public TestingStatus sendKeys(String parameter, Integer actionId) {
        currentWebElement.sendKeys(parameter);
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus click(String parameter, Integer actionId) {
        currentWebElement.click();
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByCssSelector(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.cssSelector(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementById(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.id(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByLinkText(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.linkText(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByPartialLinkText(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.partialLinkText(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByName(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.name(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByTagName(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.tagName(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByXpath(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.xpath(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByClassName(String parameter, Integer actionId) {
        currentWebElement = driver.findElement(By.className(parameter));
        return TestingStatus.PASSED;
    }
}
