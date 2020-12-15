package com.nc.project.dao.project;

import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;
import com.nc.project.model.User;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public ProjectDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public void create(Project project) {
        String sql = queryService.getQuery("project.create");
        jdbcTemplate.update(sql,
                project.getName(),
                project.getLink(),
                project.getUser().getId()
        );
    }

    @Override
    public List<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("project.getAllByPage");
        query = String.format(query, orderBy, order);

        return jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", "%" + filter + "%", size, size, page - 1},
                (resultSet, i) -> new ProjectDto(
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getTimestamp("date"),
                        new User(resultSet.getInt("user_id"),
                                resultSet.getString("email"),
                                resultSet.getString("username"),
                                resultSet.getString("surname")),
                        resultSet.getBoolean("activated")
                )
        );
    }

    @Override
    public void edit(Project project) {
        String sql = queryService.getQuery("project.edit");
        jdbcTemplate.update(sql,
                project.getName(),
                project.getLink(),
                project.getArchived(),
                project.getProjectId()
        );
    }

    @Override
    public Integer getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("project.getSizeOfResultSet");
        return jdbcTemplate.queryForObject(sql,
                new Object[]{"%" + filter + "%"},
                (rs, rowNum) -> rs.getInt("count"));
    }
}
