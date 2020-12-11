package com.nc.project.dao.testCase;

import com.nc.project.dao.actionInst.ActionInstRowMapper;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.TestCase;
import com.nc.project.service.query.QueryService;
import org.postgresql.util.PGInterval;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
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
        query = String.format(query, orderBy, order);
        List<TestCaseDto> testCaseList = jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", size, size, page - 1},
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

//    @Override
//    public void edit(TestCase testCase) {
//        String sql = queryService.getQuery("testCase.edit");
//        jdbcTemplate.update(sql,
//                testCase.getName(),
//                testCase.getIterationsAmount(),
//                testCase.getId()
//        );
//    }

    @Override
    public void delete(int test_case_id) {
        String sql = queryService.getQuery("testCase.deleteById");
        jdbcTemplate.update(sql, test_case_id);
    }

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("testCase.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%" + filter + "%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }

    @Override
    public Optional<String> getProjectLinkByTestCaseId(int id) {
        String sql = queryService.getQuery("testCase.getProjectLinkByTestCaseId");
        String link = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (rs, rowNum) -> rs.getString("link"));
        return Optional.of(link);
    }

    @Override
    public TestCase create(TestCase testCase) {
        String sql = queryService.getQuery("testCase.create");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"test_case_id"});
                    ps.setObject(1, testCase.getProject());
                    ps.setObject(2, testCase.getCreator());
                    ps.setObject(3, testCase.getStarter());
                    ps.setObject(4, testCase.getTestScenario());
                    ps.setString(5, testCase.getName());
                    ps.setTimestamp(6, testCase.getCreationDate());
                    ps.setTimestamp(7, testCase.getStartDate());
                    ps.setTimestamp(8, testCase.getFinishDate());
                    ps.setString(9, testCase.getStatus());
                    ps.setString(10, testCase.getDescription());
                    ps.setObject(11, testCase.getRecurringTime());
                    ps.setObject(12, testCase.getIterationsAmount());
                    return ps;
                },
                keyHolder);
        if(keyHolder.getKey() != null) testCase.setId((Integer) keyHolder.getKey());
        return testCase;
    }

    @Override
    public Optional<TestCase> findById(Integer id) {
        String sql = queryService.getQuery("testCase.findById");
        List<TestCase> testCases = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new TestCaseRowMapper()
        );

        return Optional.ofNullable(testCases.get(0));
    }


    @Override
    public TestCase update(TestCase testCase) {
        return null;

    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("testCase.deleteById");
        jdbcTemplate.update(sql, id);
    }
}
