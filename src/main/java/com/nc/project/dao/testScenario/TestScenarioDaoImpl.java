package com.nc.project.dao.testScenario;

import com.nc.project.dto.ProjectDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class TestScenarioDaoImpl implements TestScenarioDao {

    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public TestScenarioDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public void create(TestScenario testScenario) {
        String sql = "";
        jdbcTemplate.update(sql
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

    }

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("testScenario.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }
}
