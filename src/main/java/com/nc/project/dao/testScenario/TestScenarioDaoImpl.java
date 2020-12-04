package com.nc.project.dao.testScenario;

import com.nc.project.dto.ProjectDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.Project;
import com.nc.project.model.TestScenario;
import com.nc.project.model.User;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TestScenarioDaoImpl implements TestScenarioDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public TestScenarioDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public Optional<Integer> create(TestScenario testScenario) {
        String sql = queryService.getQuery("testScenario.create");
        Integer id =jdbcTemplate.queryForObject(sql,new Object[]{
                testScenario.getName(),
                testScenario.getUser().getId(),
                testScenario.getProject().getProject_id(),
                testScenario.getDescription()},
                (rs, rowNum) -> rs.getInt("test_scenario_id")
        );
        return Optional.of(id);
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
    public List<TestScenarioDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("testScenario.getAllByPage");
        query = String.format(query,orderBy,order);
        List<TestScenarioDto> testScenarioList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                (resultSet, i) -> new TestScenarioDto(
                        resultSet.getInt("test_scenario_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("username")
                )
        );

        return testScenarioList;
    }

    @Override
    public void edit(TestScenario testScenario) {
        String sql = queryService.getQuery("testScenario.edit");
        jdbcTemplate.update(sql,
                testScenario.getName(),
                testScenario.getDescription(),
                testScenario.isActive(),
                testScenario.getTest_scenario_id()
        );
    }

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("testScenario.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }

    @Override
    public List<TestScenarioDto> getAllByPageAndProject(int page, int size, String filter, String orderBy, String order, int projectId) {
        String query = queryService.getQuery("testScenario.getAllByPageAndProject");
        query = String.format(query,orderBy,order);
        List<TestScenarioDto> testScenarioList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%",projectId, size, size, page-1},
                (resultSet, i) -> new TestScenarioDto(
                        resultSet.getInt("test_scenario_id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("role")
                )
        );

        return testScenarioList;
    }

    @Override
    public Optional<Integer> getSizeOfProjectResultSet(String filter, int projectId) {
        String sql = queryService.getQuery("testScenario.getSizeOfProjectResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%"+filter +"%",projectId},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }

    @Override
    public Optional<Boolean> checkForTestCaseOnIt(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.checkForTestCaseOnIt");
        Boolean res =jdbcTemplate.queryForObject(sql,
                new Object[]{testScenarioId},
                (rs, rowNum) -> rs.getBoolean("case"));
        return Optional.of(res);
    }

    @Override
    public void dropActionOrCompound(int testScenarioId) {
        String sql = queryService.getQuery("testScenario.dropActionOrCompound");
        jdbcTemplate.update(sql,testScenarioId);
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
        return Optional.of(ts);
    }
}
