package com.nc.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private String name;
    private String surname;
    private String email;
    private String role;
    private Boolean activated;
    private String imageLink;
    private Timestamp regDate;
    private String aboutMe;
}
