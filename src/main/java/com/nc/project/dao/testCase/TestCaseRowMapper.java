package com.nc.project.dao.testCase;

import com.nc.project.dto.TestCaseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCaseRowMapper implements RowMapper<TestCaseDto> {
    @Override
    public TestCaseDto mapRow(ResultSet resultSet, int i) throws SQLException {
        TestCaseDto testCase = new TestCaseDto(
                resultSet.getInt("test_case_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("creator_id"),
                resultSet.getTimestamp("creation_date"),
                resultSet.getInt("iterations_amount"),
                resultSet.getString("recurring_time")
        );

        return testCase;
    }
}