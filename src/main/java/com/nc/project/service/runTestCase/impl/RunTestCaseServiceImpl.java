package com.nc.project.service.runTestCase.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.model.util.ActionType;
import com.nc.project.selenium.Context;
import com.nc.project.selenium.Executor;
import com.nc.project.selenium.Invoker;
import com.nc.project.selenium.SeleniumExecutorImpl;
import com.nc.project.service.runTestCase.RunTestCaseService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RunTestCaseServiceImpl implements RunTestCaseService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ActionInstDao actionInstDao;
    private final TestCaseDao testCaseDao;

    public RunTestCaseServiceImpl(ActionInstDao actionInstDao, TestCaseDao testCaseDao) {
        this.actionInstDao = actionInstDao;
        this.testCaseDao = testCaseDao;
        WebDriverManager.chromedriver().setup();
    }

    @Override
    @Async
    public void runTestCase(Integer id) {
        log.debug("Run test case with id="+id+" asynchronously. Thread name="+Thread.currentThread().getName());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(testCaseDao.getProjectLinkByTestCaseId(id));
        Executor executor = new SeleniumExecutorImpl(driver, new Context() {
            @Override
            public void put(String actionKey, String value) {
                log.debug("put to context: "+value);
            }

            @Override
            public Optional<String> get(String actionKey) {
                log.debug("get from context");
                return Optional.empty();
            }
        });
        Invoker invoker = new Invoker(executor);
        try {
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input",
                    "testKey1").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey2").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.SEND_KEYS,
                    "переводчик",
                    "testKey3").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.SCROLL_PAGE_TO_END,
                    "",
                    "testKey31").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tsf\"]/div[2]/div[1]/div[2]/div[2]/div[2]/center/input[1]",
                    "testKey4").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey5").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tw-sl\"]/span[2]",
                    "testKey1").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey5").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"sl_list-search-box\"]",
                    "testKey1").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey5").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.SEND_KEYS,
                    "російська",
                    "testKey3").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tw-container\"]/g-expandable-container/div/div/div[6]/g-expandable-container/div/g-expandable-content/span/div/div[3]/div[2]/div[74]/div[2]/div/b",
                    "testKey6").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey7").name());
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tw-source-text-ta\"]",
                    "testKey6").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey7").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.SEND_KEYS,
                    "тест",
                    "testKey3").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.FIND_ELEMENT_BY_XPATH,
                    "//*[@id=\"tw-src-spkr-button\"]",
                    "testKey6").name());
            Thread.sleep(1000);
            log.debug(invoker.invoke(ActionType.CLICK,
                    "",
                    "testKey7").name());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
