package com.nc.project.service.impl;

import com.nc.project.dao.UserDao;
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
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.create(user);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userByName = userDao.getUserByUsername(s);
        return new org.springframework.security.core.userdetails.User(userByName.getUsername(),
                userByName.getPassword(), userByName.getAuthorities());
    }
    
    /*@Override
    public Optional<User> getById(int id) {
		return userDao.findById(id);
	}*/

	@Override
	public void updateConfirmationToken(User user, String token) {
        RecoveryToken confToken = new RecoveryToken(token, user.getId(), new Date());
        RecoveryToken newToken = userDao.findRecoveryTokenByUserId(user.getId());
        if (newToken == null) {
            userDao.addToken(confToken);
        }
        userDao.saveToken(confToken);
        log.info("Token was updated successfully");

    }

    @Override
    public void changeUserPassword(RecoveryToken recoveryToken, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userDao.changeUserPassword(recoveryToken, encodedPassword);
    }

    @Override
    public Optional<User> getUserByRecoverPasswordToken(String token) {
        return userDao.findUserByPasswordToken(token);
    }

    @Override
    public RecoveryToken getRecoverTokenByToken(String token) {
        return userDao.findRecoverTokenByToken(token);
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
