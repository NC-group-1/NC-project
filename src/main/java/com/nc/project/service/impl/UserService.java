package com.nc.project.service.impl;

import com.nc.project.dao.UserDao;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import com.nc.project.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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
    public Optional<UserProfileDto> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public String getUserRoleByEmail(String email) {
        return userDao.getUserRoleByEmail(email);
      
    public List<UserProfileDto> getAllByPage(int page, int size) {
        return userDao.getAllByPage(page,size);
    }
      
    public void UpdateUserFromTable(UserProfileDto userProfile) {
        userDao.UpdateUserFromTable(userProfile);
    }

    @Override
    public Optional<User> findByEmailForRecovery(String email) {
        return userDao.findByEmailForRecovery(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User byEmail = userDao.findByEmailForAuth(s).get();
        return new org.springframework.security.core.userdetails.User(byEmail.getEmail(),
                byEmail.getPassword(), byEmail.getAuthorities());
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
}
