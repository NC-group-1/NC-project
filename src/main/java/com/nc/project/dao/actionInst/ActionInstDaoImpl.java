package com.nc.project.dao.actionInst;

import com.nc.project.model.ActionInst;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ActionInstDaoImpl implements ActionInstDao {
    private final QueryService queryService;
    private final JdbcTemplate jdbcTemplate;

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
}
