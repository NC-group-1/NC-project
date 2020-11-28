package com.nc.project.dao.testCase;

import com.nc.project.dto.TestCaseDto;
import com.nc.project.model.TestCase;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestCaseDaoImpl implements TestCaseDao {
    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public TestCaseDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public List<TestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("testCase.getAllByPage");
        query = String.format(query,orderBy,order);
        List<TestCaseDto> testCaseList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                (resultSet, i) -> new TestCaseDto(
                        resultSet.getInt("test_case_id"),
                        resultSet.getString("name"),
                        resultSet.getInt("creator_id"),
                        resultSet.getTimestamp("creation_date"),
                        resultSet.getInt("retries")
                )
        );

        return testCaseList;
    }

    @Override
    public void edit(TestCase testCase) {
        String sql = queryService.getQuery("testCase.edit");
        jdbcTemplate.update(sql,
                testCase.getName(),
                testCase.getRetries(),
                testCase.getId()
        );
    }

    @Override
    public void delete(int test_case_id) {
        String sql = queryService.getQuery("testCase.deleteById");
        jdbcTemplate.update(sql, test_case_id);
    }

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("testCase.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }
}