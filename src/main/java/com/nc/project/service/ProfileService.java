package com.nc.project.service;

import java.util.Optional;

import com.nc.project.model.UserProfile;

public interface ProfileService {
	Optional<UserProfile> getById(int id);
	
	Optional<UserProfile> getByEmail(String email);
}
