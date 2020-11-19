package com.nc.project.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.nc.project.model.UserProfile;

@Repository
public interface UserProfileDao {
    Optional<UserProfile> findById(int id);
}
