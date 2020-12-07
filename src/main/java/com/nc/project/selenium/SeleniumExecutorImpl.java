package com.nc.project.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Optional;

public class SeleniumExecutorImpl implements Executor{

    private final WebDriver driver;
    private WebElement currentWebElement;

    public SeleniumExecutorImpl(WebDriver driver) {
        this.driver = driver;
    }

    //TODO implement other selenium actions

    @Override
    public Optional<String> sendKeys(String parameter) {
        currentWebElement.sendKeys(parameter);
        return Optional.empty();
    }

    @Override
    public Optional<String> click(String parameter) {
        currentWebElement.click();
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByCssSelector(String parameter) {
        currentWebElement = driver.findElement(By.cssSelector(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementById(String parameter) {
        currentWebElement = driver.findElement(By.id(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByLinkText(String parameter) {
        currentWebElement = driver.findElement(By.linkText(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByPartialLinkText(String parameter) {
        currentWebElement = driver.findElement(By.partialLinkText(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByName(String parameter) {
        currentWebElement = driver.findElement(By.name(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByTagName(String parameter) {
        currentWebElement = driver.findElement(By.tagName(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByXpath(String parameter) {
        currentWebElement = driver.findElement(By.xpath(parameter));
        return Optional.empty();
    }

    @Override
    public Optional<String> findElementByClassName(String parameter) {
        currentWebElement = driver.findElement(By.className(parameter));
        return Optional.empty();
    }
}
