package com.nc.project.dao.testScenario;

import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.Project;
import com.nc.project.model.TestScenario;
import com.nc.project.model.TestScenarioComponent;
import com.nc.project.model.User;
import com.nc.project.model.util.ActionType;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
public class TestScenarioDaoImpl implements TestScenarioDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public TestScenarioDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public Integer create(TestScenario testScenario) {
        String sql = queryService.getQuery("testScenario.create");
        return jdbcTemplate.queryForObject(sql, new Object[]{
                        testScenario.getName(),
                        testScenario.getUser().getId(),
                        testScenario.getProject().getProjectId(),
                        testScenario.getDescription()},
                (rs, rowNum) -> rs.getInt("test_scenario_id")
        );
    }

    @Override
    public void addActionOrCompound(int action_compound_id, int ts_id, int order_num) {
        String sql = queryService.getQuery("testScenario.addActionOrCompound");
        jdbcTemplate.update(sql,
                action_compound_id,
                ts_id,
                order_num
        );
    }

    @Override
    public void addManyActionOrCompound(int[] action_compound_id, int ts_id) {
        String sql = queryService.getQuery("testScenario.addManyActionOrCompound");
        jdbcTemplate.update(sql,
                action_compound_id,
                ts_id,
                IntStream.range(1, action_compound_id.length + 1).toArray());
    }

    @Override
    public List<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("testScenario.getAllByPage");
        query = String.format(query, orderBy, order);
        return jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", size, size, page},
                new TestScenarioDtoRowMapper()
        );
    }

    @Override
    public void edit(TestScenario testScenario) {
        String sql = queryService.getQuery("testScenario.edit");
        jdbcTemplate.update(sql,
                testScenario.getName(),
                testScenario.getDescription(),
                testScenario.isActive(),
                testScenario.getTestScenarioId()
        );
    }

    @Override
    public Integer getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("testScenario.getSizeOfResultSet");
        return jdbcTemplate.queryForObject(sql,
                new Object[]{"%" + filter + "%"},
                (rs, rowNum) -> rs.getInt("count"));
    }

    @Override
    public List<TestScenarioDto> getAllByPageAndProject(int page, int size, String filter, String orderBy, String order, int projectId) {
        String query = queryService.getQuery("testScenario.getAllByPageAndProject");
        query = String.format(query, orderBy, order);
        List<TestScenarioDto> testScenarioList = jdbcTemplate.query(query,
                new Object[]{"%" + filter + "%", projectId, size, size, page},
                new TestScenarioDtoRowMapper()
        );

        return testScenarioList;
    }

    @Override
    public Integer getSizeOfProjectResultSet(String filter, int projectId) {
        String sql = queryService.getQuery("testScenario.getSizeOfProjectResultSet");
        return jdbcTemplate.queryForObject(sql,
                new Object[]{"%" + filter + "%", projectId},
                (rs, rowNum) -> rs.getInt("count"));
    }

    @Override
    public Boolean checkForTestCaseOnIt(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.checkForTestCaseOnIt");
        return jdbcTemplate.queryForObject(sql,
                new Object[]{testScenarioId},
                (rs, rowNum) -> rs.getBoolean("case"));
    }

    @Override
    public void dropActionOrCompound(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.dropActionOrCompound");
        jdbcTemplate.update(sql, testScenarioId);
    }

    @Override
    public void delete(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.deleteById");
        jdbcTemplate.update(sql, testScenarioId);
    }

    @Override
    public void makeUnactivated(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.makeUnactivated");
        jdbcTemplate.update(sql, testScenarioId);
    }

    @Override
    public Optional<TestScenario> getById(int id) {
        String sql = queryService.getQuery("testScenario.findById");
        TestScenario ts = jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (rs, rowNum) -> new TestScenario(
                        id,
                        rs.getString("name"),
                        rs.getString("description"),
                        new User(rs.getInt("user_id"),
                                rs.getString("user_email"),
                                rs.getString("user_name"),
                                rs.getString("user_surname")),
                        new Project(rs.getInt("project_id"),
                                rs.getString("project_name"),
                                rs.getString("project_link"),
                                rs.getTimestamp("project_date")),
                        rs.getBoolean("activated")
                )
        );
        return Optional.ofNullable(ts);
    }

    @Override
    public List<TestScenarioComponent> getComponents(int id) {
        String sql = queryService.getQuery("testScenario.getComponents");
        return jdbcTemplate.query(sql,
                new Object[]{id},
                (resultSet, i) -> new TestScenarioComponent(
                        resultSet.getInt("order_num"),
                        resultSet.getInt("action_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        ActionType.valueOf(resultSet.getString("type")),
                        resultSet.getString("key"),
                        resultSet.getInt("parameter_key_id")
                )
        );
    }
}
