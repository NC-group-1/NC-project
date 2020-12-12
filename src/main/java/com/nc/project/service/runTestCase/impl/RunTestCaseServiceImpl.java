package com.nc.project.service.runTestCase.impl;

import com.nc.project.dao.actionInst.ActionInstDao;
import com.nc.project.dao.testCase.TestCaseDao;
import com.nc.project.dto.ActionInstRunDto;
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

import java.util.List;
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
        driver.get(testCaseDao.getProjectLinkByTestCaseId(id).orElseThrow());
        List<ActionInstRunDto> actionInstRunDtos = actionInstDao.getAllByTestCaseId(id);
        Executor executor = new SeleniumExecutorImpl(driver, new Context() {
            @Override
            public void put(String actionKey, String value) {
                actionInstRunDtos.stream()
                        .filter(actionInstRunDto -> actionInstRunDto.getParameterKeyKey().equals(actionKey))
                        .findFirst().ifPresent(actionInstRunDto -> actionInstRunDto.setResult(value));
            }
            @Override
            public Optional<String> get(String actionKey) {
                return actionInstRunDtos.stream()
                        .filter(actionInstRunDto -> actionInstRunDto.getParameterKeyKey().equals(actionKey))
                        .map(ActionInstRunDto::getResult).findFirst();
            }
        });
        Invoker invoker = new Invoker(executor);
        for (ActionInstRunDto actionInst : actionInstRunDtos) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("Thread sleep error",e);
            }
            actionInst.setStatus(invoker.invoke(actionInst.getActionType(), actionInst.getParameterValue(),
                    actionInst.getParameterKeyKey()));
        }
        for (ActionInstRunDto actionInst : actionInstRunDtos) {
            log.debug(actionInst.toString());
        }
        actionInstDao.updateAll(actionInstRunDtos);
    }
}
