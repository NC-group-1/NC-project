package com.nc.project.dao.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Project project) {
        jdbcTemplate.update("INSERT INTO project (name, link, date, user_id, activated ) VALUES (?,?,?,?,?)",
                project.getName(),
                project.getLink(),
                new Timestamp(new Date().getTime()),
                project.getUser_id(),
                false
                );
    }

    @Override
    public List<Project> getAll() {
        List<Project> projectList = jdbcTemplate.query("SELECT p.project_id, p.name, p.link, p.date, p.activated, u.role FROM project p INNER JOIN usr u ON p.user_id=u.user_id", new Object[]{},
                (resultSet, i) -> new Project(
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getTimestamp("date"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("activated")
                )
        );

        return projectList;
    }

    @Override
    public void edit(Project project) {
        jdbcTemplate.update("UPDATE project SET name=?, link=?, activated=? WHERE project_id=?",
                project.getName(),
                project.getLink(),
                project.getArchived(),
                project.getProject_id()
        );
    }
}
