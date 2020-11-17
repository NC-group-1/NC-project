package com.nc.project.dao.impl;

import com.nc.project.dao.UserDao;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO user_table (username, password, role) VALUES (?,?,?)", user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM user_table WHERE username = ?", new Object[]{username}, (resultSet,i) -> {
            System.out.println(1);
            return new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
        });
        return userList.get(0);
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
                        rs.getString("confirmationToken")

                ));
        return user;
    }

    @Override
    public Optional<User> findById(int id) {
		
		try {
			User user = jdbcTemplate.queryForObject(
					"select id, username, password, role  from user_table where id = ?",
					new Object[]{id}, 
					(rs, rowNum) -> new User(
			                rs.getInt("id"),
			                rs.getString("username"),
			                rs.getString("password"),
			                rs.getString("role")
			        ));
			return Optional.of(user);
		} catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
        
    }

    @Override
    public void addToken(RecoveryToken recoveryToken) {
        this.jdbcTemplate.update("INSERT INTO recovery_token(token, user_id, expiry_date) VALUES(?,?, ?) ",
                recoveryToken, recoveryToken.getUser_id(), new Timestamp(new Date().getTime()));
    }

    @Override
    public void saveToken(RecoveryToken recoveryToken) {
        this.jdbcTemplate.update("UPDATE recovery_token, expiry_date SET token=?, expiry_date=? WHERE id=?",
                recoveryToken, new Timestamp(new Date().getTime()), recoveryToken.getUser_id());
    }

    @Override
    public void changeUserPassword(RecoveryToken recoveryToken, String password) {
        this.jdbcTemplate.update("UPDATE user SET password=? WHERE id=?", password, recoveryToken.getUser_id());
    }

    @Override
    public Optional<User> findUserByPasswordToken(String token) {
        try {
            User user = jdbcTemplate.queryForObject(
                    "SELECT id, username, password, role, email, enabled, confirmation_token " +
                            "FROM user_table WHERE confirmationToken = ?",
                    new Object[]{token},
                    (rs, rowNum) -> new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("email"),
                            rs.getBoolean("enabled"),
                            rs.getString("confirmationToken")
                    ));
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public RecoveryToken findTokenByRecoverPasswordToken(String token) {
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
                        "FROM recovery_token WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("token"),
                        rs.getInt("user_id"),
                        rs.getDate("expiry_date")
                ));
        return recoveryToken;

    }
}
