package com.nc.project.dao;

import com.nc.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    void create(User user);
    User getUserByUsername(String username);
}
