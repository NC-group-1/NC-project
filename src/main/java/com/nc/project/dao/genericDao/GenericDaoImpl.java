package com.nc.project.dao.genericDao;

import com.nc.project.dao.GenericDao;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<ID extends Serializable, E extends GenericDaoEntity<E, ID>> implements GenericDao<E, ID> {

    protected final JdbcTemplate jdbcTemplate;
    protected final QueryService queryService;
    private E voidEntity;

    public GenericDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService, E voidEntity) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
        this.voidEntity = voidEntity;
    }

    @Override
    public E create(E entity) {
        String sql = queryService.getQuery(entity.returnTableName()+".create");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql,
                            new String[]{entity.returnTableName()+"_id"});
                    Object[] arr = entity.returnQueryArgs();
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
        String sql = queryService.getQuery(voidEntity.returnTableName()+".findById");
        List<E> result = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setObject(1,id),
                voidEntity.returnRowMapper());
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public E update(E entity) {
        String sql = queryService.getQuery(entity.returnTableName()+".edit");
        jdbcTemplate.update(sql, entity.returnQueryArgs());
        return entity;
    }

    @Override
    public void delete(ID id) {
        String sql = queryService.getQuery(voidEntity.returnTableName()+".deleteById");
        jdbcTemplate.update(sql, id);
    }
}
