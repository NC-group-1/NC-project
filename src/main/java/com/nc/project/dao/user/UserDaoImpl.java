package com.nc.project.dao.user;


import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.service.query.QueryService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final QueryService queryService;

    public UserDaoImpl(JdbcTemplate jdbcTemplate, QueryService queryService) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryService = queryService;
    }

    @Override
    public void create(User user) {
        String sql = queryService.getQuery("user.create");
        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getRole(),
                UUID.randomUUID().toString(),
                new Timestamp(new Date().getTime() + 720000));
    }

    public Optional<UserProfileDto> updatePersonalProfileById(UserProfileDto userProfileDto) {
        String sql = queryService.getQuery("user.updatePersonalProfileById");
        int update = jdbcTemplate.update(sql, userProfileDto.getName(), userProfileDto.getSurname(), userProfileDto.getAboutMe(), userProfileDto.getUserId());
        return update > 0 ? Optional.of(userProfileDto) : Optional.empty();
    }

    @Override
    public Optional<UserProfileDto> findByEmail(String email) {
        String sql = queryService.getQuery("user.findByEmail");
        UserProfileDto userProfile = jdbcTemplate.queryForObject(sql, new Object[]{email},
                new UserRowMapper());
        return Optional.of(userProfile);
    }

    @Override
    public Optional<UserProfileDto> findUserProfileById(int id) {
        String sql = queryService.getQuery("user.findUserProfileById");
        UserProfileDto userProfile = jdbcTemplate.queryForObject(sql, new Object[]{id},
                new UserRowMapper());
        return Optional.of(userProfile);
    }

    @Override
    public String getUserRoleByEmail(String email) {
        String sql = queryService.getQuery("user.getUserRoleByEmail");
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{email},
                (rs, rowNum) -> rs.getString("role"));
    }

    @Override
    public Optional<User> findByEmailForAuth(String email) {
        String sql = queryService.getQuery("user.findByEmailForAuth");
        User user = jdbcTemplate.queryForObject(
                sql,
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
    public Integer getUserIdByEmail(String email) {
        String sql = queryService.getQuery("user.getUserIdByEmail");
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, (resultSet, i) -> resultSet.getInt("user_id"));
    }

    @Override
    public Optional<User> findByEmailForRecovery(String email) {
        System.out.println(email);
        String sql = queryService.getQuery("user.findByEmailForRecovery");
        User user = jdbcTemplate.queryForObject(
                sql,
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
        String sql = queryService.getQuery("user.saveToken");
        this.jdbcTemplate.update(sql,
                recoveryToken.getToken(), new Timestamp(new Date().getTime() + 60000), recoveryToken.getUser_id());
    }

    @Override
    public void changeUserPassword(int userId, String password) {
        String sql = queryService.getQuery("user.changeUserPassword");
        this.jdbcTemplate.update(sql, password, userId);
    }

    @Override
    public Optional<Integer> findUserIdByPasswordToken(String token) {
        String sql = queryService.getQuery("user.findUserIdByPasswordToken");
        Integer userId = jdbcTemplate.queryForObject(
                sql,
                new Object[]{token},
                (rs, rowNum) -> rs.getInt("user_id"));
        return Optional.of(userId);
    }

    @Override
    public RecoveryToken findTokenByRecoverPasswordToken(String token) {
        String sql = queryService.getQuery("user.findTokenByRecoverPasswordToken");
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{token},
                (rs, rowNum) -> new RecoveryToken(
                        rs.getString("email_code"),
                        rs.getInt("user_id"),
                        rs.getDate("code_expire_date")
                ));
    }

    @Override
    public List<UserProfileDto> getAllByPage(int page, int size,String filter ,String orderBy, String order) {
        String query = queryService.getQuery("user.getAllByPage");
        query = String.format(query,orderBy,order);
        List<UserProfileDto> listUserProfile = jdbcTemplate.query(query,
                new Object[]{"%"+filter +"%", size, size, page-1},
                new UserRowMapper()
        );
        return listUserProfile;
    }

    @Override
    public void updateUserFromTable(UserProfileDto userProfile) {
        String sql = queryService.getQuery("user.updateUserFromTable");
        jdbcTemplate.update(sql,
                userProfile.getName(),
                userProfile.getSurname(),
                userProfile.getActivated(),
                userProfile.getEmail(),
                userProfile.getUserId());
    }

    @Override
    public Optional<Integer> getSizeOfResultSet(String filter) {
        String sql = queryService.getQuery("user.getSizeOfResultSet");
        Integer size = jdbcTemplate.queryForObject(sql,
                new Object[]{"%"+filter +"%"},
                (rs, rowNum) -> rs.getInt("count"));
        return Optional.of(size);
    }
}
