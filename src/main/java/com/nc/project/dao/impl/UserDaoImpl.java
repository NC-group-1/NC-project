package com.nc.project.dao.impl;

import com.nc.project.dao.UserDao;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.model.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO user_table (username, email, role) VALUES (?,?,?);", user.getEmail(), user.getEmail(), user.getRole());
        jdbcTemplate.update("INSERT INTO recovery_token (token, expiry_date, user_id) VALUES (?,?, (SELECT id from user_table WHERE email = ?));", UUID.randomUUID().toString(), new Timestamp(new Date().getTime() + 60000), user.getEmail());
    }

    @Override
    public User getUserByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM user_table WHERE username = ?", new Object[]{username}, (resultSet, i) -> {
            return new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
        });
    }

    @Override
    public User findByEmail(String email) {

        User user = jdbcTemplate.queryForObject(
                "SELECT id, username, password, role, email, enabled, confirmation_token " +
                        "FROM user_table WHERE email = ?",
                new Object[]{email},
                (rs, rowNum) -> new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getBoolean("enabled"),
                        rs.getString("confirmation_token")
                ));
        return user;
    }

    @Override
    public void addToken(RecoveryToken recoveryToken) {
        this.jdbcTemplate.update("INSERT INTO recovery_token(token, user_id, expiry_date) VALUES(?,?, ?) ",
                recoveryToken, recoveryToken.getUser_id(), new Timestamp(new Date().getTime()));
    }

    @Override
    public void saveToken(RecoveryToken recoveryToken) {
        this.jdbcTemplate.update("UPDATE recovery_token SET token=?, expiry_date=? WHERE user_id=?",
                recoveryToken.getToken(), new Timestamp(new Date().getTime()), recoveryToken.getUser_id());
    }

    @Override
    public void changeUserPassword(RecoveryToken recoveryToken, String password) {
        this.jdbcTemplate.update("UPDATE user_table SET password=? WHERE id=?", password, recoveryToken.getUser_id());
    }

    @Override
    public Optional<User> findUserByPasswordToken(String token) {
        try {
            User user = jdbcTemplate.queryForObject(
                    "SELECT user_table.id, username, password, role, email, enabled, confirmation_token " +
                            "FROM user_table JOIN recovery_token ON user_table.id = recovery_token.user_id WHERE recovery_token.token = ?",
                    new Object[]{token},
                    (rs, rowNum) -> new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getBoolean("enabled"),
                            rs.getString("confirmation_token")
                    ));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public RecoveryToken findTokenByRecoverPasswordToken(String token) {
        return jdbcTemplate.queryForObject(
                "SELECT token, user_id, expiry_date " +
                        "FROM recovery_token WHERE token = ?",
                new Object[]{token},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getDate("expiry_date")
                ));
    }

    @Override
    public RecoveryToken findRecoverTokenByToken(String token) {
        RecoveryToken recoveryToken = jdbcTemplate.queryForObject(
                "SELECT token, user_id, expiry_date " +
                        "FROM recovery_token WHERE token = ?",
                new Object[]{token},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getDate("expiry_date")
                ));
        return recoveryToken;
    }

    @Override
    public RecoveryToken findRecoveryTokenByUserId(int id) {
        RecoveryToken recoveryToken = jdbcTemplate.queryForObject(
                "SELECT token, user_id, expiry_date " +
                        "FROM recovery_token WHERE user_id = ?",
                new Object[]{id},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getDate("expiry_date")
                ));
        return recoveryToken;

    }
}
