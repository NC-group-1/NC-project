package com.nc.project.dao.runningTestCase;

import com.nc.project.dto.RunningTestCaseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RunningTestCaseRowMapper implements RowMapper<RunningTestCaseDto> {
    @Override
    public RunningTestCaseDto mapRow(ResultSet resultSet, int i) throws SQLException {
        RunningTestCaseDto runningTestCase = new RunningTestCaseDto(
                resultSet.getInt("test_case_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("starter_id"),
                resultSet.getInt("watcher_numb"),
                resultSet.getString("status")

        );

        return runningTestCase;
    }
}