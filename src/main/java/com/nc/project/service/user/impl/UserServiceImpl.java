package com.nc.project.service.user.impl;


import com.nc.project.dao.user.UserDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.service.notification.NotificationService;
import com.nc.project.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private NotificationService notificationService;

    public UserServiceImpl(UserDao userDao, PasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User createUser(User user) {
        userDao.create(user);
        return user;
    }

    @Override
    public Optional<UserProfileDto> updatePersonalProfile(UserProfileDto user) {
        return userDao.updatePersonalProfileById(user);
    }

    @Override
    public Optional<UserProfileDto> findUserProfileById(int id) {
        return userDao.findUserProfileById(id);
    }

    @Override
    public Optional<UserProfileDto> findByEmail(String email) throws InterruptedException {
        notificationService.sendUserTestCasesProgress(1);
        return userDao.findByEmail(email);
    }

    @Override
    public String getUserRoleByEmail(String email) {
        return userDao.getUserRoleByEmail(email);
    }

    @Override
    public Page<UserProfileDto> getAllByPage(int page, int size, String filter ,String orderBy,String order) {
        if (orderBy.equals(""))
            orderBy = "user_id";
        if (!order.equals("DESC")) {
            order = "";
        }
        return new Page(userDao.getAllByPage(page, size, filter, orderBy, order), userDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void updateUserFromTable(UserProfileDto userProfile) {
        userDao.updateUserFromTable(userProfile);
    }

    @Override
    public Integer getUserIdByEmail(String email) {
        return userDao.getUserIdByEmail(email);
    }

    @Override
    public Optional<User> findByEmailForRecovery(String email) {
        return userDao.findByEmailForRecovery(email);
    }

    @Override
    public void updateConfirmationToken(User user, String token) {
        RecoveryToken confToken = new RecoveryToken(token, user.getId(), new Date());
        userDao.saveToken(confToken);
        log.info("Token was updated successfully");

    }

    @Override
    public void changeUserPassword(int userId, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userDao.changeUserPassword(userId, encodedPassword);
    }

    @Override
    public Optional<Integer> getUserIdByRecoverPasswordToken(String token) {
        return userDao.findUserIdByPasswordToken(token);
    }

    @Override
    public String validatePasswordRecoverToken(String token) {
        final RecoveryToken passToken = userDao.findTokenByRecoverPasswordToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(RecoveryToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(RecoveryToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    @Override
    public Optional<String> addLinkToEmail(String link, String pathToEmail) {
        File file = new File(pathToEmail);

        StringBuilder htmlStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while (reader.ready()) {
                htmlStringBuilder.append(reader.readLine());
            }
            return Optional.of(String.format(htmlStringBuilder.toString(), link));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

}
