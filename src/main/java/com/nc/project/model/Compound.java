package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compound extends Action{
    private List<ActionOfCompound> actions;
    private ParameterKey parameterKey;
    public Compound(int id, String name, String description, ActionType type, ParameterKey parameterKey, List<ActionOfCompound> actions) {
        super(id, name, description, type, parameterKey);
        this.actions = actions;
    }
    public Compound(int id, String name, String description, ActionType type, ParameterKey parameterKey) {
        super(id, name, description, type, parameterKey);
    }
    public Compound(int id, String name, String description, ActionType type) {
        super(id, name, description, type);
    }
    @JsonIgnore
    public Integer[] getActionsId() {
        return this.actions.stream().map(actionOfCompound -> actionOfCompound.getAction().getId()).toArray(Integer[]::new);
    }
    @JsonIgnore
    public Integer[] getActionsOrder() {
        return this.actions.stream().map(ActionOfCompound::getOrderNum).toArray(Integer[]::new);
    }
    @JsonIgnore
    public Integer[] getActionsKeyIds() {
        return this.actions.stream().map(actionOfCompound -> ParameterKey.checkValid(actionOfCompound.getParameterKey())
                ? actionOfCompound.getParameterKey().getId()
                : ParameterKey.checkValid(actionOfCompound.getAction().getParameterKey())
                ? actionOfCompound.getAction().getParameterKey().getId() :
                null).toArray(Integer[]::new);
    }
    @JsonIgnore
    public String[] getActionsKeys() {
        return this.actions.stream().map(actionOfCompound -> ParameterKey.checkValid(actionOfCompound.getParameterKey())
                ? actionOfCompound.getParameterKey().getKey()
                : ParameterKey.checkValid(actionOfCompound.getAction().getParameterKey())
                ? actionOfCompound.getAction().getParameterKey().getKey() :
                null).toArray(String[]::new);
    }
}
