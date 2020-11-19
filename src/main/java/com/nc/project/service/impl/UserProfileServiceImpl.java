package com.nc.project.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nc.project.dao.UserProfileDao;
import com.nc.project.model.UserProfile;
import com.nc.project.service.ProfileService;

@Service
public class UserProfileServiceImpl implements ProfileService {
	private UserProfileDao userProfileDao;

	@Autowired
	UserProfileServiceImpl( UserProfileDao userProfileDao){
		this.userProfileDao = userProfileDao;
	};
	
	@Override
    public Optional<UserProfile> getById(int id) {
		return userProfileDao.findById(id);
	}
}
