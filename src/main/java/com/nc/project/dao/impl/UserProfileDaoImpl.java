package com.nc.project.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nc.project.dao.UserProfileDao;
import com.nc.project.model.UserProfile;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {
	private JdbcTemplate jdbcTemplate;

	@Autowired
	UserProfileDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Optional<UserProfile> findById(int id) {

		try {
			UserProfile userProfile = jdbcTemplate.queryForObject(
					"SELECT name, surname, role, reg_date, activated, about_me, image_link FROM usr WHERE user_id = ?",
					new Object[] { id },
					(rs, rowNum) -> new UserProfile(rs.getString("name"), 
													rs.getString("surname"),
													rs.getString("role"),
													rs.getDate("reg_date"), 
													rs.getBoolean("activated"), 
													rs.getString("about_me"),
													rs.getString("image_link")
													));
			return Optional.of(userProfile);
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
