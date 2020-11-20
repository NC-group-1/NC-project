package com.nc.project.service;

import java.util.Optional;

import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;

public interface IUserService {

    User createUser(User user);

    User findByEmail(String email);

    void updateConfirmationToken(User user, String token);

    void changeUserPassword(RecoveryToken recoveryToken, String password);

    Optional<User> getUserByRecoverPasswordToken(String token);

    RecoveryToken getRecoverTokenByToken(String token);

    String validatePasswordRecoverToken(String token);

}
