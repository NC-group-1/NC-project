package com.nc.project.selenium;

import com.nc.project.model.util.TestingStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumExecutorImpl implements Executor{

    private final WebDriver driver;
    private final Context context;
    private WebElement currentWebElement;

    public SeleniumExecutorImpl(WebDriver driver, Context context) {
        this.driver = driver;
        this.context = context;
    }

    //TODO implement other selenium actions

    @Override
    public TestingStatus compareWithActionResult(String parameter, String actionKey) {
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus compareWithString(String parameter, String actionKey) {

        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus switchTab(String parameter, String actionKey) {
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus sendKeys(String parameter, String actionKey) {
        currentWebElement.sendKeys(parameter);
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus click(String parameter, String actionKey) {
        currentWebElement.click();
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByCssSelector(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.cssSelector(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementById(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.id(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByLinkText(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.linkText(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByPartialLinkText(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.partialLinkText(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByName(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.name(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByTagName(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.tagName(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByXpath(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.xpath(parameter));
        return TestingStatus.PASSED;
    }

    @Override
    public TestingStatus findElementByClassName(String parameter, String actionKey) {
        currentWebElement = driver.findElement(By.className(parameter));
        return TestingStatus.PASSED;
    }
}
