package com.nc.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDto {
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String role;
    private Boolean activated;
    private String imageLink;
    private Timestamp regDate;
    private String aboutMe;

    public UserProfileDto(int id, String name, String surname, String role) {
        this.userId = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }
}
