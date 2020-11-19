package com.nc.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryToken {

    private int id;

    private String token;

    private int user_id;

    private Date expiryDate;

    public RecoveryToken(String token, int user_id, Date expiryDate) {
        this.token = token;
        this.user_id = user_id;
        this.expiryDate = expiryDate;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

}
