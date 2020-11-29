package com.nc.project.dao.parameterKey;

import com.nc.project.model.ParameterKey;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParameterKeyDaoImpl implements ParameterKeyDao {
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    public ParameterKeyDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ParameterKey create(ParameterKey key) {
        String sql = queryService.getQuery("parameterKey.create");
        //SqlParameterSource parameters = new BeanPropertySqlParameterSource(key);
        jdbcTemplate.update(sql,
                key.getKey()
        );
        return key;

    }

    @Override
    public Optional<ParameterKey> findById(Integer id) {
        String sql = queryService.getQuery("parameterKey.findById");
        List<ParameterKey> keys = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ParameterKeyRowMapper());

        return Optional.ofNullable(keys.get(0));

    }

    @Override
    public ParameterKey update(ParameterKey key) {
        String sql = queryService.getQuery("parameterKey.edit");
        jdbcTemplate.update(sql,
                key.getKey()
        );

        return key;
    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("parameterKey.deleteById");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ParameterKey> getKeysByName(String name) {
        String sql = queryService.getQuery("parameterKey.findByName");
        List<ParameterKey> keys = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setString(1, name),
                new ParameterKeyRowMapper()
        );

        return keys.isEmpty() ? null : keys;
    }
}
