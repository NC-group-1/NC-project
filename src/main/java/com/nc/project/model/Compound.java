package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compound extends Action{
    private ActionOfCompound[] actions;
    @JsonIgnore
    private ParameterKey key;
    public Compound(int id, String name, String description, ActionType type, ParameterKey key, ActionOfCompound[] actions) {
        super(id, name, description, type, key);
        this.actions = actions;
    }
    public Compound(int id, String name, String description, ActionType type, ParameterKey key) {
        super(id, name, description, type, key);
    }
    public Compound(int id, String name, String description, ActionType type) {
        super(id, name, description, type);
    }
    @JsonIgnore
    public Integer[] getActionsId() {
        return Arrays.stream(this.actions).map(actionOfCompound -> actionOfCompound.getAction().getId()).toArray(Integer[]::new);
    }
    @JsonIgnore
    public Integer[] getActionsOrder() {
        return Arrays.stream(this.actions).map(ActionOfCompound::getOrderNum).toArray(Integer[]::new);
    }
    @JsonIgnore
    public Integer[] getActionsKeyIds() {
        return Arrays.stream(actions).map(actionOfCompound -> ParameterKey.checkValid(actionOfCompound.getKey())
                ? actionOfCompound.getKey().getId()
                : ParameterKey.checkValid(actionOfCompound.getAction().getKey())
                ? actionOfCompound.getAction().getKey().getId() :
                null).toArray(Integer[]::new);
    }
    @JsonIgnore
    public String[] getActionsKeys() {
        return Arrays.stream(actions).map(actionOfCompound -> ParameterKey.checkValid(actionOfCompound.getKey())
                ? actionOfCompound.getKey().getKey()
                : ParameterKey.checkValid(actionOfCompound.getAction().getKey())
                ? actionOfCompound.getAction().getKey().getKey() :
                null).toArray(String[]::new);
    }
}
