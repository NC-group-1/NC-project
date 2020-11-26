package com.nc.project.service;

import java.util.List;
import java.util.Optional;

import com.nc.project.dto.Page;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;

public interface IUserService {

    User createUser(User user);

    Optional<UserProfileDto> updatePersonalProfile(UserProfileDto user);

    Optional<UserProfileDto> findUserProfileById(int id);

    Optional<User> findByEmailForRecovery(String email);

    void updateConfirmationToken(User user, String token);

    void changeUserPassword(int userId, String password);

    Optional<Integer> getUserIdByRecoverPasswordToken(String token);

    String validatePasswordRecoverToken(String token);

    Optional<UserProfileDto> findByEmail(String email);

    String getUserRoleByEmail(String email);

    Page<UserProfileDto> getAllByPage(int page, int size);

    void UpdateUserFromTable(UserProfileDto userProfile);
}
