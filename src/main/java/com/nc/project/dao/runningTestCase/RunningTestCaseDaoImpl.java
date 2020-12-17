package com.nc.project.dao.runningTestCase;

import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RunningTestCaseDaoImpl implements RunningTestCaseDao {
    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public RunningTestCaseDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public List<RunningTestCaseDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        String query = queryService.getQuery("runningTestCase.getAllByPage");
        query = String.format(query,orderBy,order);
        List<RunningTestCaseDto> runningTestCaseList = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                new RunningTestCaseRowMapper()
        );
        return runningTestCaseList;
    }


    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String query = queryService.getQuery("runningTestCase.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(query,
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }

    @Override
    public void edit(TestCase testCase) {
        String query = queryService.getQuery("runningTestCase.edit");
            jdbcTemplate.update(query,
                    testCase.getStatus(),
                    testCase.getId()
            );
    }

    @Override
    public List<UserProfileDto> getWatcherByTestCaseId(int test_case_id) {
        String query = queryService.getQuery("runningTestCase.getWatcherByTestCaseId");
        List<UserProfileDto> watcherList = jdbcTemplate.query(query,
                new Object[]{test_case_id},
                (resultSet, i) -> new UserProfileDto(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role")
                )
        );

        return watcherList;
    }

    @Override
    public List<UserProfileDto> getWatcherWithImageByTestCaseId(int test_case_id) {
        String query = queryService.getQuery("runningTestCase.getWatcherWithImageByTestCaseId");
        return jdbcTemplate.query(query,
                new Object[]{test_case_id},
                (resultSet, i) -> { UserProfileDto profileDto = new UserProfileDto();
                    profileDto.setUserId(resultSet.getInt("user_id"));
                    profileDto.setName(resultSet.getString("name"));
                    profileDto.setSurname(resultSet.getString("surname"));
                    profileDto.setImageLink(resultSet.getString("image_link"));
                    return  profileDto;
                });
    }

    @Override
    public List<UserProfileDto> getUsersByName(String name) {
        String query = queryService.getQuery("runningTestCase.findByName");
        List<UserProfileDto> users = jdbcTemplate.query(query,
                new Object[]{name},
                (resultSet, i) -> new UserProfileDto(
                        resultSet.getInt("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("role")
                )
        );

        return users;
    }

    @Override
    public void addWatcher(Watcher watcher) {
        String query = queryService.getQuery("runningTestCase.addWatcher");
        jdbcTemplate.update(query,
                watcher.getUser_id(),
                watcher.getTest_case_id()
        );
    }
}