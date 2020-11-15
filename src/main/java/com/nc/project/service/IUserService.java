package com.nc.project.service;

import java.util.Optional;

import com.nc.project.model.User;

public interface IUserService {
    void createUser(User user);
    
    public Optional<User> getById(int id);
}
