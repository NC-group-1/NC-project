package com.nc.project.dao.action;

import com.nc.project.model.Action;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

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
        Integer keyId = null;
        if(action.getParameterKey() != null) keyId = action.getParameterKey().getId();
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
        Integer count = jdbcTemplate.queryForObject(sql, new Object[] {}, Integer.class);
        return isNull(count) ? 0 : count;
    }

    @Override
    public int findNumberOfElements(String filter, String filterTable, Boolean includeCompounds) {
        String query = queryService.getQuery("action.findNumberOfElementsWithFilter");
        if(includeCompounds){
            query = String.format(query,filterTable,"");
        } else {
            query = String.format(query,filterTable,"AND action.type<>'COMPOUND' ");
        }
        Integer size = jdbcTemplate.queryForObject(query,
                new Object[]{"%"+filter+"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return size == null? 0:size;
    }

    /*
    public List<Action> findAllActionsByPage(int limit, int offset) {
        String sql = queryService.getQuery("action.findAllByPage");
        return jdbcTemplate.query(sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, limit);
                    preparedStatement.setInt(2, offset);
                }, new ActionRowMapper());
    }
     */

    @Override
    public Action setActionName(Integer id, String name) {
        String sql = queryService.getQuery("action.setActionName");
        jdbcTemplate.update(sql,
                id, name);
        return this.findById(id).get();
    }

    @Override
    public Action setActionDescription(Integer id, String description) {
        String sql = queryService.getQuery("action.setActionDescription");
        jdbcTemplate.update(sql,
                id, description);
        return this.findById(id).get();
    }

    @Override
    public List<Action> findAllActionsByPage(int limit, int offset, int targetId) {
        String sql = queryService.getQuery("action.findAllByPageWithoutTarget");
        return jdbcTemplate.query(sql,
                preparedStatement -> {
                    preparedStatement.setInt(1, targetId);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                }, new ActionRowMapper());
    }

    @Override
    public List<Action> findAllActionsByPage(int limit, int offset, String filter, String filterTable,
                                             String orderBy, String order, Boolean includeCompounds) {
        String query = queryService.getQuery("action.findAllByPageWithFilter");
        if(includeCompounds){
            query = String.format(query,filterTable,"",orderBy,order);
        } else {
            query = String.format(query,filterTable,"AND action.type<>'COMPOUND' ",orderBy,order);
        }
        query = String.format(query,filterTable,orderBy,order);
        return jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", limit, offset},
                new ActionRowMapper()
        );
    }

    @Override
    public Action update(Action action) {
        String sql = queryService.getQuery("action.edit");
        Integer keyId = null;
        if(action.getParameterKey() != null) keyId = action.getParameterKey().getId();
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

        return actions.size() == 0 ? Optional.empty() : Optional.ofNullable(actions.get(0));
    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("action.deleteById");
        jdbcTemplate.update(sql, id);
    }

}
