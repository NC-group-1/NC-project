package com.nc.project.service.user;

import com.nc.project.dto.Page;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.User;

import java.util.Optional;

public interface UserService {

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

    Page<UserProfileDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    void UpdateUserFromTable(UserProfileDto userProfile);

    Optional<String> addLinkToEmail(String link, String pathToEmail);

}
