package com.nc.project.dao.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(Project project) {
        jdbcTemplate.update("INSERT INTO project (name, link, user_id ) VALUES (?,?,?)",
                project.getName(),
                project.getLink(),
                project.getUser_id()
                );
    }

    @Override
    public List<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy,String order) {
        String query = String.format("SELECT p.project_id, p.name, p.link, p.date, p.activated, u.name username " +
                "FROM project p INNER JOIN usr u ON p.user_id=u.user_id WHERE p.name LIKE ? ORDER BY %s %s LIMIT ? OFFSET ?*?",orderBy,order);
        List<ProjectDto> projectList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                (resultSet, i) -> new ProjectDto(
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getTimestamp("date"),
                        resultSet.getString("username"),
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

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        Integer size = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM project WHERE name LIKE ?",
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }
}
