package com.nc.project.dao.dataSet;

import com.nc.project.dao.parameterKey.ParameterKeyRowMapper;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.model.DataSet;
import com.nc.project.model.ParameterKey;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class DataSetDaoImpl implements DataSetDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public DataSetDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public List<DataSetGeneralInfoDto> getGeneralInfoByPage(int limit,
                                                            int offset,
                                                            String filter,
                                                            String orderBy,
                                                            String order) {
        String query = queryService.getQuery("data_set.getGeneralInfoByPage");
        query = String.format(query,orderBy,order);
        return jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", limit, offset},
                new DataSetGeneralInfoRowMapper()
        );
    }

    @Override
    public int getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("data_set.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{filter},
                (rs, rowNum) -> rs.getInt("count"));
        return size == null? 0:size;
    }

    @Override
    public DataSetGeneralInfoDto create(DataSetGeneralInfoDto entity) {
        String sql = queryService.getQuery("data_set.create");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"data_set_id"});
                    ps.setObject(1, entity.getName());
                    ps.setObject(2, entity.getDescription());
                    ps.setObject(3, entity.getCreatedById());
                    return ps;
                },
                keyHolder);
        if(keyHolder.getKey() != null) entity.setId((Integer) keyHolder.getKey());
        return entity;
    }

    @Override
    public Optional<DataSetGeneralInfoDto> findById(Integer id) {
        String sql = queryService.getQuery("data_set.findById");
        List<DataSetGeneralInfoDto> result = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new DataSetGeneralInfoRowMapper());
        return Optional.ofNullable(result.get(0));
    }

    @Override
    public DataSetGeneralInfoDto update(DataSetGeneralInfoDto entity) {
        String sql = queryService.getQuery("data_set.edit");
        jdbcTemplate.update(sql,
                new Object[]{entity.getName(), entity.getDescription(), entity.getCreatedById(), entity.getId()}
        );

        return entity;
    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("data_set.deleteById");
        jdbcTemplate.update(sql, id);
    }
}
