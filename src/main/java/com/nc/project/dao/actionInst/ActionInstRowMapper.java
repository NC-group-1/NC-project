package com.nc.project.dao.actionInst;

import com.nc.project.model.ActionInst;
import com.nc.project.model.ParameterKey;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionInstRowMapper implements RowMapper<ActionInst> {

    @Override
    public ActionInst mapRow(ResultSet resultSet, int i) throws SQLException {
        ActionInst actionInst = new ActionInst();
        actionInst.setId(resultSet.getObject("action_inst_id", Integer.class));
        actionInst.setAction(resultSet.getObject("action_id", Integer.class));
        actionInst.setCompound(resultSet.getObject("compound_id", Integer.class));
        actionInst.setTestCase(resultSet.getObject("test_case_id", Integer.class));
        actionInst.setDataSet(resultSet.getObject("data_set_id", Integer.class));
        //actionInst.setDataSet(Arrays.asList((Integer[]) resultSet.getArray("data_set_id").getArray()));
        actionInst.setParameterKey(new ParameterKey(
                resultSet.getObject("parameter_key_id", Integer.class),
                resultSet.getObject("key", String.class)
        ));
        actionInst.setStatus(resultSet.getString("status"));
        actionInst.setOrderNum(resultSet.getObject("order_num", Integer.class));


        return actionInst;

    }
}
