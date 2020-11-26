package com.nc.project.dao.compound;

import com.nc.project.dto.Page;
import com.nc.project.model.Action;
import com.nc.project.model.Compound;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompoundDaoImpl implements CompoundDao {
    private SimpleJdbcInsert actionInsert;
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public CompoundDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    //rewrite
    @Override
    public Action createCompound(Action compound) {
        String sql = queryService.getQuery("compound.create");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(compound);
        jdbcTemplate.update(sql,
                compound.getName(),
                compound.getDescription(),
                ActionType.FIRST_TYPE.toString()
        );
//        int id = actionInsert.executeAndReturnKey(parameters).intValue();
//        compound.setId(id);
        return compound;
    }

    @Override
    public Action findCompoundById(int id) {
        String sql = queryService.getQuery("compound.findById");
        Optional<Action> compoundAsAction = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (resultSet, i) -> Optional.of(new Action(
                        resultSet.getInt("action_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        ActionType.FIRST_TYPE
                )));
        return compoundAsAction.orElse(new Action());
    }

    @Override
    public List<Action> findAllCompounds() {
        String sql = queryService.getQuery("compound.findAll");
        return jdbcTemplate.query(sql, (resultSet, i) -> new Action(
                resultSet.getInt("action_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                ActionType.FIRST_TYPE
        ));
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
    public List<Action> getActionsOfCompound(int compoundId) {
        String sql = queryService.getQuery("compound.findCompoundActions");
        return jdbcTemplate.query(sql, new Object[]{compoundId}, (resultSet, i) -> new Action(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                ActionType.FIRST_TYPE
        ));
    }

    @Override
    public void postActionInCompound(Compound compound) {
        String sql = queryService.getQuery("compound.addActionToCompound");
        jdbcTemplate.update(sql,
                compound.getActionId(),
                compound.getCompoundId(),
                compound.getOrderNum(),
                compound.getKey().getKey()
        );
    }

    @Override
    public void deleteActionFromCompound(Compound compound) {
        String sql = queryService.getQuery("compound.deleteActionFromCompound");
        jdbcTemplate.update(sql, compound.getActionId(), compound.getCompoundId());
    }

    @Override
    public List<Action> getCompoundsByPage(int limit, int offset) {
        String sql = queryService.getQuery("compound.findByPage");
        return jdbcTemplate.query(sql, new Object[]{limit, offset}, (resultSet, i) -> new Action(
                resultSet.getInt("action_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                ActionType.COMPOUND
        ));
    }

    @Override
    public Integer getNumberOfCompounds() {
        String sql = queryService.getQuery("compound.getNumberOfCompounds");
        return jdbcTemplate.queryForObject(sql, (resultSet, i) -> resultSet.getInt("count"));
    }
}
