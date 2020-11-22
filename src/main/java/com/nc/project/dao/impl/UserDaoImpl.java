package com.nc.project.dao.impl;

import com.nc.project.dao.UserDao;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.model.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO usr (email, role, reg_date, email_code, code_expire_date) VALUES (?,?,?,?,?)",
                user.getEmail(),
                user.getRole(),
                new Timestamp(new Date().getTime()),
                UUID.randomUUID().toString(),
                new Timestamp(new Date().getTime() + 60000));
    }

    @Override
    public Optional<UserProfileDto> findByEmail(String email) {
        UserProfileDto userProfile = jdbcTemplate.queryForObject("SELECT name, surname, email, role, activated, image_link, reg_date, about_me FROM usr WHERE email = ?", new Object[]{email},
                (resultSet, i) -> new UserProfileDto(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("activated"),
                        resultSet.getString("image_link"),
                        resultSet.getTimestamp("reg_date"),
                        resultSet.getString("about_me")
                ));
        return Optional.of(userProfile);
    }

    @Override
    public Optional<User> findByEmailForAuth(String email) {
        User user = jdbcTemplate.queryForObject(
                "SELECT email, pass, role, activated " +
                        "FROM usr WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> new User(
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getBoolean("activated"),
                        rs.getString("pass")
                ));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailForRecovery(String email) {
        User user = jdbcTemplate.queryForObject(
                "SELECT user_id, email, email_code, code_expire_date " +
                        "FROM usr WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> new User(
                        rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("email_code"),
                        rs.getTimestamp("code_expire_date")
                ));
        return Optional.of(user);

    }

    @Override
    public void saveToken(RecoveryToken recoveryToken) {
        this.jdbcTemplate.update("UPDATE usr SET email_code=?, code_expire_date=? WHERE user_id=?",
                recoveryToken.getToken(), new Timestamp(new Date().getTime() + 60000), recoveryToken.getUser_id());
    }

    @Override
    public void changeUserPassword(int userId, String password) {
        this.jdbcTemplate.update("UPDATE usr SET pass=? WHERE user_id=?", password, userId);
    }

    @Override
    public Optional<Integer> findUserIdByPasswordToken(String token) {
        Integer userId = jdbcTemplate.queryForObject(
                "SELECT user_id FROM usr WHERE email_code = ?",
                new Object[]{token},
                (rs, rowNum) -> rs.getInt("user_id"));
        return Optional.of(userId);
    }

    @Override
    public RecoveryToken findTokenByRecoverPasswordToken(String token) {
        return jdbcTemplate.queryForObject(
                "SELECT user_id, email_code, code_expire_date " +
                        "FROM usr WHERE email_code = ?",
                new Object[]{token},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("email_code"),
                        rs.getInt("user_id"),
                        rs.getDate("code_expire_date")
                ));
    }
}
