package com.nc.project.dao;

import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.model.UserProfile;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao {
    void create(User user);

    User getUserByUsername(String username);

    User findByEmail(String email);

    void addToken(RecoveryToken recoveryToken);

    void saveToken(RecoveryToken recoveryToken);

    void changeUserPassword(RecoveryToken recoveryToken, String password);

    Optional<User> findUserByPasswordToken(String token);

    RecoveryToken findTokenByRecoverPasswordToken(String token);

    RecoveryToken findRecoverTokenByToken(String token);

    RecoveryToken findRecoveryTokenByUserId(int id);
}
