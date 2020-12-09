package com.nc.project.dao.testCase;

import com.nc.project.model.TestCase;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCaseRowMapper implements RowMapper<TestCase> {

    @Override
    public TestCase mapRow(ResultSet resultSet, int i) throws SQLException {
        TestCase testCase = new TestCase();
        testCase.setId(resultSet.getObject("test_case_id", Integer.class));
        //testCase.setProject(resultSet.getObject("project_id", Integer.class));
        testCase.setCreator(resultSet.getObject("creator_id", Integer.class));
        testCase.setStarter(resultSet.getObject("starter_id", Integer.class));
        testCase.setTestScenario(resultSet.getObject("test_scenario_id", Integer.class));
        testCase.setName(resultSet.getString("name"));
        testCase.setCreationDate(resultSet.getTimestamp("creation_date"));
        testCase.setStartDate(resultSet.getTimestamp("start_date"));
        testCase.setFinishDate(resultSet.getTimestamp("finish_date"));
        testCase.setStatus(resultSet.getString("status"));
        testCase.setDescription(resultSet.getString("description"));
        testCase.setIterationsAmount(resultSet.getObject("iterations_amount", Integer.class));

        return testCase;

    }
}
