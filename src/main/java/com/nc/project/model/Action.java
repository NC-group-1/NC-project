package com.nc.project.model;

import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private int id;
    private String name;
    private String description;
    private ActionType type;
    private ParameterKey parameterKey;

    public Action(int id, String name, String description, ActionType type){
        this.id=id;
        this.name=name;
        this.description=description;
        this.type=type;
    }

}
