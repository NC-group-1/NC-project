package com.nc.project.selenium;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    private final String browser;

    public WebDriverFactory(String browser) {
        this.browser = browser;
    }

    public WebDriver initWebDriver(DesiredCapabilities capabilities) {
        WebDriver webDriver = null;

        if (StringUtils.startsWith(browser, "http://") || StringUtils.startsWith(browser, "https://")) {
            try {
                webDriver = new RemoteWebDriver(new URL(browser), capabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (BrowserType.CHROME.equalsIgnoreCase(browser)) {
                ChromeOptions options = new ChromeOptions();
                options.merge(capabilities);
                webDriver = new ChromeDriver(options);
            } else {
                throw new RuntimeException("Unsupported browser: " + browser);
            }
        }

        return webDriver;
    }

}

