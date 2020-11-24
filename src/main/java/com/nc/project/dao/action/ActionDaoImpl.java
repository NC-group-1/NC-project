package com.nc.project.dao.action;

import com.nc.project.model.Action;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActionDaoImpl implements ActionDao {
    private SimpleJdbcInsert actionInsert;
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public ActionDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Action createAction(Action action) {
        String sql = queryService.getQuery("action.create");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(action);
        jdbcTemplate.update(sql,
                action.getName(),
                action.getDescription(),
                action.getType().toString(),
                action.getKey().toString());
        int id = actionInsert.executeAndReturnKey(parameters).intValue();
        action.setId(id);
        return action;
    }

    @Override
    public Action getActionByKey(String key) {
        String sql = queryService.getQuery("action.findByKey");
        List<Action> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1, key),
                new ActionRowMapper());
        return actions.isEmpty() ? null : actions.get(0);
    }

    @Override
    public List<Action> findAllActionsByPage(int size, int number) {
        String sql = queryService.getQuery("action.findAll");
        return jdbcTemplate.query(sql,
                new ActionRowMapper());
    }

    @Override
    public Action editAction(Action action) {
        String sql = queryService.getQuery("action.edit");
        jdbcTemplate.update(sql,
                 action.getName(),
                 action.getDescription(),
                 action.getType().toString(),
                 action.getKey().toString()
        );

        return action;

    }

    @Override
    public Action findActionById(int id) {
        String sql = queryService.getQuery("action.findById");
        List<Action> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActionRowMapper());
        return actions.isEmpty() ? null : actions.get(0);
    }

    @Override
    public void removeAction(int id) {
        String sql = queryService.getQuery("action.deleteById");
        jdbcTemplate.update(sql, id);
    }

}
