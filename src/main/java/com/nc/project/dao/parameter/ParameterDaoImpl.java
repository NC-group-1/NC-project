package com.nc.project.dao.parameter;

import com.nc.project.dao.genericDao.GenericDaoImpl;
import com.nc.project.model.Parameter;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class ParameterDaoImpl extends GenericDaoImpl<Integer, Parameter> implements ParameterDao{

    public ParameterDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        super(jdbcTemplate, queryService, new Parameter());
    }

    @Override
    public List<Parameter> getAllByDataSetId(int id) {
        String sql = queryService.getQuery("parameter.getAllByDataSetId");
        return jdbcTemplate.query(sql,
                new Object[]{id},
                new Parameter().returnRowMapper());
    }

}
