package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParameterKey {
    private Integer id;
    private String key;

    public static boolean checkValid(ParameterKey parameterKey){
        if (parameterKey == null) {
            return false;
        }else return !(parameterKey.getId() == null || parameterKey.getId() == 0);
    }
}
