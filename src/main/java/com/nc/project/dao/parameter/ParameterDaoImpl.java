package com.nc.project.dao.parameter;

import com.nc.project.model.Parameter;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParameterDaoImpl implements ParameterDao{

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public ParameterDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public List<Parameter> getAllByDataSetId(int id) {
        String sql = queryService.getQuery("parameter.getAllByDataSetId");
        return jdbcTemplate.query(sql,
                new Object[]{id},
                new ParameterRowMapper());
    }

    @Override
    public Parameter create(Parameter entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<Parameter> findById(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Parameter update(Parameter entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
