package com.nc.project.dao.actionInst;

import com.nc.project.dto.ActionInstRunDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.util.ActionType;
import com.nc.project.model.util.TestingStatus;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActionInstDaoImpl implements ActionInstDao {
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

    RowMapper<ActionInstRunDto> actionInstRunDtoRowMapper = (resultSet, i) -> new ActionInstRunDto(
            resultSet.getObject("action_inst_id", Integer.class),
            ActionType.valueOf(resultSet.getString("type")),
            resultSet.getString("value"),
            resultSet.getString("key"),
            TestingStatus.valueOf(resultSet.getString("status")),
            resultSet.getString("result")
    );

    public ActionInstDaoImpl(QueryService queryService, JdbcTemplate jdbcTemplate) {
        this.queryService = queryService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ActionInst create(ActionInst actionInst) {
        String sql = queryService.getQuery("actionInst.create");
        jdbcTemplate.update(sql,
                actionInst.getAction(),
                actionInst.getCompound(),
                actionInst.getTestCase(),
                actionInst.getDataSet(),
                actionInst.getParameterKey(),
                actionInst.getStatus(),
                actionInst.getOrderNum()
        );
        return actionInst;
    }

    @Override
    public List<ActionInst> getAllActionInstancesByTestCaseId(Integer testCaseId) {
        String sql = queryService.getQuery("actionInst.getAllByTestCaseId");
        List<ActionInst> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, testCaseId),
                new ActionInstRowMapper()
        );

        return actions;

    }

    @Override
    public Optional<ActionInst> findById(Integer id) {
        String sql = queryService.getQuery("actionInst.findById");
        List<ActionInst> actions = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ActionInstRowMapper()
        );

        return Optional.ofNullable(actions.get(0));
    }

    @Override
    public ActionInst update(ActionInst actionInst) {
        String sql = queryService.getQuery("actionInst.edit");
        jdbcTemplate.update(sql,
                actionInst.getAction(),
                actionInst.getCompound(),
                actionInst.getTestCase(),
                actionInst.getDataSet(),
                actionInst.getParameterKey(),
                actionInst.getStatus(),
                actionInst.getOrderNum(),
                actionInst.getId()

                );

        return actionInst;
    }

    @Override
    public void delete(Integer id) {
        String sql = queryService.getQuery("actionInst.deleteById");
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ActionInstRunDto> getAllByTestCaseId(int id) {
        String sql = queryService.getQuery("actionInst.getAllByDataSetId");
        return jdbcTemplate.query(sql,
                new Object[]{id},
                actionInstRunDtoRowMapper);
    }

    @Override
    public List<ActionInstRunDto> updateAll(List<ActionInstRunDto> actionInstRunDtos) {
        String sql = queryService.getQuery("actionInst.updateAll");
        jdbcTemplate.update(sql,
                actionInstRunDtos.stream().map(ActionInstRunDto::getId).toArray(Integer[]::new),
                actionInstRunDtos.stream().map(actionInstRunDto -> actionInstRunDto.getStatus().name())
                        .toArray(String[]::new),
                actionInstRunDtos.stream().map(ActionInstRunDto::getResult).toArray(String[]::new));
        return actionInstRunDtos;
    }
}
