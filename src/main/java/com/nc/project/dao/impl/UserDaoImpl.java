package com.nc.project.dao.impl;

import com.nc.project.dao.UserDao;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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

    //@Override
    public List<UserProfileDto> getAllByPage22(int page, int size) {
        List<UserProfileDto> listUserProfile = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT name, surname, email, role, activated, image_link, reg_date, about_me FROM usr LIMIT ? OFFSET ?*?",
                size,size,(page-1));
        for (Map row : rows) {
            listUserProfile.add(new UserProfileDto(
                    (String)row.get("name"),
                    (String)row.get("surname"),
                    (String)row.get("email"),
                    (String)row.get("role"),
                    (boolean)row.get("activated"),
                    (String)row.get("image_link"),
                    (Timestamp)row.get("reg_date"),
                    (String)row.get("about_me")));
        }
        return listUserProfile;
    }

    @Override
    public List<UserProfileDto> getAllByPage(int page, int size) {
        List<UserProfileDto> listUserProfile = jdbcTemplate.query("SELECT name, surname, email, role, activated, image_link, reg_date, about_me FROM usr LIMIT ? OFFSET ?*?",
                new Object[]{size,size,page-1},
                (resultSet, i) -> new UserProfileDto(
                        resultSet.getString("name"),
                        resultSet.getString("surname"),
                        resultSet.getString("email"),
                        resultSet.getString("role"),
                        resultSet.getBoolean("activated"),
                        resultSet.getString("image_link"),
                        resultSet.getTimestamp("reg_date"),
                        resultSet.getString("about_me")
                )
        );
        return listUserProfile;
    }
}
