package com.nc.project.dao.parameter;

import com.nc.project.dao.genericDao.GenericDaoImpl;
import com.nc.project.model.Parameter;
import com.nc.project.model.ParameterKey;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ParameterDaoImpl extends GenericDaoImpl<Integer, Parameter> implements ParameterDao{

    RowMapper<Parameter> rowMapper = (resultSet, i) -> new Parameter(
            resultSet.getObject("parameter_id", Integer.class),
            new ParameterKey(
                    resultSet.getObject("parameter_key_id", Integer.class),
                    resultSet.getString("key")
            ),
            resultSet.getString("value"),
            resultSet.getObject("data_set_id", Integer.class)
    );

    public ParameterDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        super(jdbcTemplate, queryService);
    }

    @Override
    protected RowMapper<Parameter> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected String getTableName() {
        return "parameter";
    }

    @Override
    protected Object[] getQueryArgs(Parameter entity) {
        return new Object[]{entity.getValue(), entity.getKey().getId(), entity.getDataSetId(), entity.getId()};
    }

    @Override
    public List<Parameter> getAllByDataSetId(int id) {
        String sql = queryService.getQuery("parameter.getAllByDataSetId");
        return jdbcTemplate.query(sql,
                new Object[]{id},
                getRowMapper());
    }

    @Override
    public int getNumberOfUsages(int id) {
        String sql = queryService.getQuery("parameter.getNumberOfUsages");
        Integer count = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (rs, rowNum) -> rs.getInt("count"));
        return count == null? 0:count;
    }

}
