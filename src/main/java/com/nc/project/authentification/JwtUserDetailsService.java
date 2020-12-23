package com.nc.project.authentification;

import com.nc.project.dao.user.UserDao;
import com.nc.project.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public JwtUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmailForAuth(email).get();
        if (user.getActivated()){
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), user.getAuthorities());
        } else throw new UsernameNotFoundException("username not found");

    }
}
