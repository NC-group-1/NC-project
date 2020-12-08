package com.nc.project.dao.dataSet;

import com.nc.project.dao.genericDao.GenericDaoImpl;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.model.DataSet;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DataSetDaoImpl extends GenericDaoImpl<Integer, DataSetGeneralInfoDto> implements DataSetDao {

    public DataSetDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        super(jdbcTemplate, queryService, new DataSetGeneralInfoDto());
    }

    @Override
    public List<DataSet> getByIds(List<Integer> dataSetIds) {
        String sql = queryService.getQuery("dataSet.getByIds");
        String inSql = String.join(",", Collections.nCopies(dataSetIds.size(), "?"));
        List<DataSet> dataSets = jdbcTemplate.query(String.format(sql, inSql),
                dataSetIds.toArray(), new DataSetRowMapper()
        );

        return dataSets;

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
                new DataSetGeneralInfoDto().returnRowMapper()
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
}
