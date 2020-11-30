package com.nc.project.dao.dataSet;

import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.model.DataSet;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public DataSet create(DataSet entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Optional<DataSet> findById(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public DataSet update(DataSet entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
