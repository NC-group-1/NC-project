package com.nc.project.dao.testCase;

import com.nc.project.dto.ActionInstDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestCase;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.postgresql.util.PGInterval;
import java.sql.SQLException;

import java.sql.PreparedStatement;
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
    public List<TestCase> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("testCase.getAllByPage");
        query = String.format(query,orderBy,order);
        List<TestCase> testCaseList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                new TestCaseRowMapper()
        );
        return testCaseList;
    }

    @Override
    public void edit(TestCase testCase) {
        String sql = queryService.getQuery("testCase.edit");

        try {
            jdbcTemplate.update(sql,
                    testCase.getName(),
                    testCase.getIterationsAmount(),
                    new PGInterval(testCase.getRecurringTime()),
                    testCase.getStartDate(),
                    testCase.getStatus(),
                    testCase.getId()

            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
    public Boolean editTestCaseActions(TestScenarioDto testScenarioDto) {
        String sql = queryService.getQuery("testCase.editTestCaseActions");
        return jdbcTemplate.update(sql, testScenarioDto.getTestCaseId(),
                testScenarioDto.getActions().stream().mapToInt(action -> action.getParameterKey().getId()),
                testScenarioDto.getActions().stream().mapToInt(ActionInstDto::getDatasetId),
                testScenarioDto.getActions().size()) > 0;
    }

    @Override
    public TestCase create(TestCase testCase) {
        String sql = queryService.getQuery("testCase.create");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[]{"test_case_id"});
                   // ps.setObject(1, testCase.getProject());
                    ps.setObject(1, testCase.getCreator());
                    ps.setObject(2, testCase.getStarter());
                    ps.setObject(3, testCase.getTestScenario());
                    ps.setString(4, testCase.getName());
                    ps.setTimestamp(5, testCase.getCreationDate());
                    ps.setTimestamp(6, testCase.getStartDate());
                    ps.setTimestamp(7, testCase.getFinishDate());
                    ps.setString(8, testCase.getStatus().name());
                    ps.setString(9, testCase.getDescription());
                    ps.setObject(10, testCase.getRecurringTime());
                    ps.setObject(11, testCase.getIterationsAmount());
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
        String sql = queryService.getQuery("testCase.edit");
        jdbcTemplate.update(sql, testCase.getStarter(),
                testCase.getStartDate(), testCase.getFinishDate(), testCase.getStatus().name(), testCase.getId());
        return testCase;
    }

    @Override
    public void delete(Integer test_case_id) {
        String sql = queryService.getQuery("testCase.deleteById");
        jdbcTemplate.update(sql, test_case_id);
    }


    @Override
    public List<Integer> getTestCasesIdByWatcher(Integer userId) {
        String sql = queryService.getQuery("notification.getRunningTestCasesIdByWatcher");
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet,i) -> resultSet.getInt("test_case_id"));
    }
}
