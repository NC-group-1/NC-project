package com.nc.project.model;

import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestScenarioComponent {
    private Compound action;
    private int orderNum;

    public TestScenarioComponent(int orderNum, int action_id, String name, String description, ActionType actionType, String key, int parameter_key_id) {
        this.orderNum = orderNum;
        action = new Compound(action_id, name, description, actionType);
        if (key != null) {
            action.setParameterKey(new ParameterKey(parameter_key_id, key));
        }

    }

    public TestScenarioComponent(Action action, int orderNum) {
        this.action = new Compound(action.getId(),action.getName(),action.getDescription(),action.getType(),action.getParameterKey());
        this.orderNum = orderNum;
    }
}
