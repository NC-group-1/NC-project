package com.nc.project.controller;

import com.nc.project.dto.AuthRequest;
import com.nc.project.dto.AuthResponse;
import com.nc.project.model.User;
import com.nc.project.ServiceImpl.JwtService;
import com.nc.project.ServiceImpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody User user){
        userService.createUser(user);
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public AuthResponse authenticate(@RequestBody AuthRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwtService.generateToken(req.getUsername());
        return new AuthResponse(token);
    }
}
