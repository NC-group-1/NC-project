package com.nc.project.dao.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Project project) {
//        jdbcTemplate.update("INSERT INTO project (name, link, date, user_id, activated ) VALUES (?,?,?,?,?)",
//                project.getName(),
//                project.getLink(),
//                new Timestamp(new Date().getTime()),
//                project.getUser().getId(),
//                false
//                );

        System.out.println(project);

    }
}
