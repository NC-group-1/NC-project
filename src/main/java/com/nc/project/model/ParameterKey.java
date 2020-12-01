package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterKey {
    @Id
    private Integer id;
    private String key;

    public static boolean checkValid(ParameterKey parameterKey){
        if (parameterKey == null) {
            return false;
        }else return !(parameterKey.getId() == null || parameterKey.getId() == 0);
    }
}
