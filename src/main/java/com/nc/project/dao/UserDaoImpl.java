package com.nc.project.dao;

import com.nc.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO user_table (username, password, role) VALUES (?,?,?)", user.getUsername(), user.getPassword(), user.getRole());
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> userList = jdbcTemplate.query("SELECT * FROM user_table WHERE username = ?", new Object[]{username}, (resultSet,i) -> {
            System.out.println(1);
            return new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("role"));
        });
        return userList.get(0);
    }
}
