package com.nc.project.dao.compound;

import com.nc.project.model.Action;
import com.nc.project.model.ActionOfCompound;
import com.nc.project.model.Compound;
import com.nc.project.model.ParameterKey;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class CompoundDaoImpl implements CompoundDao {
    private SimpleJdbcInsert actionInsert;
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public CompoundDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Compound createCompound(Compound compound) {
        String sql = queryService.getQuery("compound.create");
        jdbcTemplate.update(sql,
                compound.getName(),
                compound.getDescription(),
                compound.getActionsId(),
                compound.getActionsOrder(),
                compound.getActionsKeys()
        );
        return compound;
    }

    @Override
    public Compound findCompoundById(int id) {
        String sql = queryService.getQuery("compound.findById");
        return jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                new CompoundRowMapper());
    }

    @Override
    public List<Compound> findAllCompounds() {
        String sql = queryService.getQuery("compound.findAll");
        return jdbcTemplate.query(sql, new CompoundRowMapper());
    }

    @Override
    public Action editCompound(Action compound) {
        String sql = queryService.getQuery("compound.edit");
        jdbcTemplate.update(sql,
                compound.getName(),
                compound.getDescription(),
                compound.getId()
        );
        return compound;
    }

    @Override
    public void removeCompound(int id) {
        String sql = queryService.getQuery("compound.deleteById");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ActionOfCompound> getActionsOfCompound(int compoundId) {
        String sql = queryService.getQuery("compound.findCompoundActions");
        return jdbcTemplate.query(sql, new Object[]{compoundId}, (resultSet, i) ->
                new ActionOfCompound(
                        new Action(
                                resultSet.getInt("action_id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                ActionType.valueOf(resultSet.getString("type"))
                        ),
                        resultSet.getInt("order_num"),
                        new ParameterKey(
                                resultSet.getInt("pk_id"),
                                resultSet.getString("key")
                        )
                )
        );
    }

    @Override
    public void postActionInCompound(ActionOfCompound compound, int compoundId) {

        String sql = compound.getKey() != null
                ? queryService.getQuery("compound.addActionToCompound")
                : queryService.getQuery("compound.addActionWithKeyNull");
        jdbcTemplate.update(sql,
                compound.getKey() != null ? compound.getKey().getKey() : null,
                compound.getAction().getId(),
                compoundId,
                compound.getOrderNum()
        );
    }

    @Override
    public void deleteActionFromCompound(int actionId, int compoundId) {
        String sql = queryService.getQuery("compound.deleteActionFromCompound");
        jdbcTemplate.update(sql, actionId, compoundId);
    }

    @Override
    public List<Compound> getCompoundsByPage(int limit, int offset, String filterName, String filterDescription, String orderByWithDirection) {
//        String sql = queryService.getQuery("compound.findByPage");
        String sql = "SELECT DISTINCT action.action_id, name, description FROM" +
                " action INNER JOIN compound_action ON action.action_id = compound_action.compound_id" +
                " WHERE lower(name) LIKE lower(concat('%',?::varchar,'%'))" +
                " AND lower(description) LIKE lower(concat('%',?::varchar,'%'))" +
                " ORDER BY " + orderByWithDirection + " LIMIT ? OFFSET ?;";
        return jdbcTemplate.query(sql, new Object[]{filterName, filterDescription, limit, offset}, new CompoundRowMapper());
    }

    @Override
    public Integer getNumberOfCompounds(String name, String description) {
        String sql = queryService.getQuery("compound.getNumberOfCompounds");
        return jdbcTemplate.queryForObject(sql, new Object[]{name, description}, (resultSet, i) -> resultSet.getInt("count"));
    }

    @Override
    public void editActionsOrderInCompound(Action[] actions, int compoundId) {
        String sql = queryService.getQuery("compound.changeOrder");
        jdbcTemplate.update(sql, Arrays.stream(actions).map(Action::getId).toArray(Integer[]::new), compoundId);
    }

    @Override
    public void changeActions(ActionOfCompound[] actions, Integer compoundId) {
        String sql = queryService.getQuery("compound.changeActions");
        jdbcTemplate.update(sql,
                compoundId,
                Arrays.stream(actions).map(actionOfCompound -> actionOfCompound.getAction().getId()).toArray(Integer[]::new),
                compoundId,
                Arrays.stream(actions).map(ActionOfCompound::getOrderNum).toArray(Integer[]::new),
                Arrays.stream(actions).map(actionOfCompound -> ParameterKey.checkValid(actionOfCompound.getKey())
                        ? actionOfCompound.getKey().getId()
                        : ParameterKey.checkValid(actionOfCompound.getAction().getKey())
                        ? actionOfCompound.getAction().getKey().getId() :
                        null).toArray(Integer[]::new));
    }

}
