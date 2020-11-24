package com.nc.project.service.query.impl;

import com.nc.project.service.query.QueryService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Resource
    private Environment env;

    @Override
    public String getQuery(String name) {
        return this.env.getRequiredProperty(name);
    }
}
