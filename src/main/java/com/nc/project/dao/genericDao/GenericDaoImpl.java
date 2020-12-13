package com.nc.project.dao.genericDao;

import com.nc.project.dao.GenericDao;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public abstract class GenericDaoImpl<ID extends Serializable, E extends GenericDaoEntity<ID>> implements GenericDao<E, ID> {

    protected final JdbcTemplate jdbcTemplate;
    protected final QueryService queryService;

    public GenericDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public E create(E entity) {
        String sql = queryService.getQuery(getTableName()+".create");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql,
                            new String[]{getTableName()+"_id"});
                    Object[] arr = getQueryArgs(entity);
                    for (int i = 1; i<arr.length; i++){
                        ps.setObject(i, arr[i-1]);
                    }
                    return ps;
                },
                keyHolder);
        if(keyHolder.getKey() != null) entity.setId((ID)keyHolder.getKey());
        return entity;
    }

    @Override
    public Optional<E> findById(ID id) {
        String sql = queryService.getQuery(getTableName()+".findById");
        List<E> result = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setObject(1,id),
                getRowMapper());
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public E update(E entity) {
        String sql = queryService.getQuery(getTableName()+".edit");
        jdbcTemplate.update(sql, getQueryArgs(entity));
        return entity;
    }

    @Override
    public void delete(ID id) {
        String sql = queryService.getQuery(getTableName()+".deleteById");
        jdbcTemplate.update(sql, id);
    }

    protected abstract RowMapper<E> getRowMapper();

    protected abstract String getTableName();

    protected abstract Object[] getQueryArgs(E entity);
}
