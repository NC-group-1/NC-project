package com.nc.project.dao.action;

import com.nc.project.model.Action;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActionDaoImpl implements ActionDao {
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public ActionDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Action create(Action action) {
        String sql = queryService.getQuery("action.create");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(action);
        Integer keyId = null;
        if(action.getKey() != null) keyId = action.getKey().getId();
        jdbcTemplate.update(sql,
                action.getName(),
                action.getDescription(),
                action.getType().toString(),
                keyId
        );
        return action;
    }

    @Override
    public List<Action> getActionByName(String name) {
        String sql = queryService.getQuery("action.findByName");
        List<Action> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new ActionRowMapper()
        );
        return actions.isEmpty() ? null : actions;
    }

    @Override
    public int findNumberOfElements() {
        String sql = queryService.getQuery("action.findNumberOfElements");
        return jdbcTemplate.queryForObject(sql,
                (resultSet, i) -> resultSet.getInt("count"));
    }

    @Override
    public List<Action> findAllActionsByPage(int limit, int offset) {
        String sql = queryService.getQuery("action.findAllByPage");
        return jdbcTemplate.query(sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, limit);
                    preparedStatement.setInt(2, offset);
                }, new ActionRowMapper());
    }

    @Override
    public Action setActionName(int id, String name) {
        String sql = queryService.getQuery("action.setActionName");
        jdbcTemplate.update(sql,
                id, name);
        return this.findById(id).get();
    }

    @Override
    public Action setActionDescription(int id, String description) {
        String sql = queryService.getQuery("action.setActionDescription");
        jdbcTemplate.update(sql,
                id, description);
        return this.findById(id).get();
    }

    @Override
    public Action update(Action action) {
        String sql = queryService.getQuery("action.edit");
        Integer keyId = null;
        if(action.getKey() != null) keyId = action.getKey().getId();
        jdbcTemplate.update(sql,
                action.getName(),
                action.getDescription(),
                action.getType().toString(),
                keyId,
                action.getId()
        );
        return action;

    }

    @Override
    public Optional<Action> findById(Integer id) {
        String sql = queryService.getQuery("action.findById");
        List<Action> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActionRowMapper()
        );

        return Optional.ofNullable(actions.get(0));
    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("action.deleteById");
        jdbcTemplate.update(sql, id);
    }

}
