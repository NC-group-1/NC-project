package com.nc.project.dao.compound;

import com.nc.project.model.Compound;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

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
//rewrite
    @Override
    public Compound createCompound(Compound compound) {
        String sql = queryService.getQuery("compound.create");
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(compound);
        jdbcTemplate.update(sql,
                compound.getActionId(),
                compound.getOrderNum(),
                compound.getKey().toString());
        int id = actionInsert.executeAndReturnKey(parameters).intValue();
        compound.setCompoundId(id);
        return compound;
    }

    @Override
    public Compound findActionById(int id) {
        String sql = queryService.getQuery("compound.findById");
        List<Compound> compounds = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new CompoundRowMapper());
        return compounds.isEmpty() ? null : compounds.get(0);
    }

    @Override
    public List<Compound> findAllCompounds() {
        String sql = queryService.getQuery("compound.findAll");
        return jdbcTemplate.query(sql, new CompoundRowMapper());
    }

    @Override
    public Compound editCompound(Compound compound) {
        String sql = queryService.getQuery("compound.edit");
        jdbcTemplate.update(sql,
                compound.getActionId(),
                compound.getCompoundId(),
                compound.getOrderNum(),
                compound.getKey().toString()
        );

        return compound;
    }

    @Override
    public void removeCompound(int id) {
        String sql = queryService.getQuery("compound.deleteById");
        jdbcTemplate.update(sql, id);
    }
}
