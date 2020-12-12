package com.nc.project.dao.testScenario;

import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestScenarioDtoRowMapper implements RowMapper<TestScenarioDto> {
    @Override
    public TestScenarioDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new TestScenarioDto(
                resultSet.getInt("test_scenario_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                new User(resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("surname"),
                        resultSet.getString("email")));
    }
}
