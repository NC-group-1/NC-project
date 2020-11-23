package com.nc.project.service.user;

import com.nc.project.dao.user.UserDao;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.RecoveryToken;
import com.nc.project.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private PasswordEncoder bCryptPasswordEncoder;

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
    public Optional<UserProfileDto> findByEmail(String email) {
        return userDao.findByEmail(email);
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
}
