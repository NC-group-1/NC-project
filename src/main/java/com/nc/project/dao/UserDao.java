package com.nc.project.dao;

import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.model.UserProfile;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao {
    void create(User user);

    Optional<User> findByEmailForAuth(String email);
    Optional<User> findByEmailForRecovery(String email);
    Optional<UserProfileDto> findByEmail(String email);

    void saveToken(RecoveryToken recoveryToken);

    void changeUserPassword(int userId, String password);

    Optional<Integer> findUserIdByPasswordToken(String token);

    RecoveryToken findTokenByRecoverPasswordToken(String token);

}
