package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {
    private int id;
    private String name;
    private String description;
    private ActionType type;
    private ParameterKey key;

    public Action(int id, String name, String description, ActionType type){
        this.id=id;
        this.name=name;
        this.description=description;
        this.type=type;
    }

}
