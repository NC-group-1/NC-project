package com.nc.project.daoImpl;

import com.nc.project.dao.UserDao;
import com.nc.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    
    @Override
    public Optional<User> findById(int id) {
		
		try {
			User user = jdbcTemplate.queryForObject(
					"select id, username, password, role  from user_table where id = ?",
					new Object[]{id}, 
					(rs, rowNum) -> new User(
			                rs.getInt("id"),
			                rs.getString("username"),
			                rs.getString("password"),
			                rs.getString("role")
			        ));
			return Optional.of(user);
		} catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
        
    }
}
