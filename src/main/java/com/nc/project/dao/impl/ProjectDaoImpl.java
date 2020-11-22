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
                project.getRole(),
                false
                );

    }

    @Override
    public List<Project> getAll() {
        List<Project> projectList = jdbcTemplate.query("SELECT p.name, p.link, p.date, p.activate, u.role FROM project p INNER JOIN usr u ON project.user_id=usr.user_id ", new Object[]{},
                (resultSet, i) -> new Project(
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getTimestamp("date"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("activate")
                )
        );

//        List<Project> projectList = new ArrayList<>();
//        projectList.add(new Project("name1","link1",new Timestamp(new Date().getTime()),"Admin",false));
//        projectList.add(new Project("name2","link2",new Timestamp(new Date().getTime()),"Manager",false));
//        projectList.add(new Project("name3","link3",new Timestamp(new Date().getTime()),"Engineer",false));

        return projectList;
    }

    @Override
    public void edit(Project project) {
        jdbcTemplate.update("UPDATE project SET name=?, link=?, activated=?",
                project.getName(), project.getLink(), project.getArchived());
    }
}
