package com.nc.project.dao.dataSet;

import com.nc.project.dao.genericDao.GenericDaoImpl;
import com.nc.project.dto.DataSetGeneralInfoDto;
import com.nc.project.dto.DataSetParamDto;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DataSetDaoImpl extends GenericDaoImpl<Integer, DataSetGeneralInfoDto> implements DataSetDao {

    RowMapper<DataSetGeneralInfoDto> rowMapper = (resultSet, i) -> new DataSetGeneralInfoDto(
            resultSet.getObject("data_set_id", Integer.class),
            resultSet.getString("name"),
            resultSet.getString("description"),
            resultSet.getObject("user_id", Integer.class),
            resultSet.getString("role"),
            resultSet.getString("username"),
            resultSet.getString("surname")
    );

    public DataSetDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        super(jdbcTemplate, queryService);
    }

    @Override
    protected RowMapper<DataSetGeneralInfoDto> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected String getTableName() {
        return "data_set";
    }

    @Override
    protected Object[] getQueryArgs(DataSetGeneralInfoDto entity) {
        return new Object[]{entity.getName(), entity.getDescription(), entity.getCreatedById(), entity.getId()};
    }

//    @Override
//    public List<DataSet> getByIds(List<Integer> dataSetIds) {
//        String sql = queryService.getQuery("dataSet.getByIds");
//        String inSql = String.join(",", Collections.nCopies(dataSetIds.size(), "?"));
//        List<DataSet> dataSets = jdbcTemplate.query(String.format(sql, inSql),
//                dataSetIds.toArray(), new DataSetRowMapper()
//        );
//
//        return dataSets;
//
//    }

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
                getRowMapper()
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
    public int getNumberOfUsages(int id) {
        String sql = queryService.getQuery("data_set.getNumberOfUsages");
        Integer count = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (rs, rowNum) -> rs.getInt("count"));
        return count == null? 0:count;
    }

    @Override
    public DataSetParamDto getDatasetValueByParam(int id, int parameterId) {
        String sql = queryService.getQuery("data_set.getDatasetValueByParam");
        return jdbcTemplate.queryForObject(sql, new Object[]{id, parameterId}, (resultSet, i) -> new DataSetParamDto(
                resultSet.getInt("ds_id"),
                resultSet.getString("name"),
                resultSet.getString("val")
        ));
    }
}
