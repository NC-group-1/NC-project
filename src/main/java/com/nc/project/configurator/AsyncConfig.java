package com.nc.project.configurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("Exception in async method. ", throwable);
            log.error("Method name=" + method.getName());
            for (Object param : objects) {
                log.error("Parameter value=" + param);
            }
        };
    }
}
