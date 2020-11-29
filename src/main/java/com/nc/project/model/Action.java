package com.nc.project.model;

import com.nc.project.model.util.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    @Id
    private Integer id;
    private String name;
    private String description;
    private ActionType type;
    private ParameterKey key;

}
