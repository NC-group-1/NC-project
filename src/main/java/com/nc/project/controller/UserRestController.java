package com.nc.project.controller;

import com.nc.project.authentification.JwtTokenUtil;
import com.nc.project.dto.AuthRequest;
import com.nc.project.dto.AuthResponse;
import com.nc.project.model.User;
import com.nc.project.model.UserProfile;
import com.nc.project.service.ProfileService;
import com.nc.project.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService userProfileService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody User user){
        userService.createUser(user);
    }
    
    @GetMapping("{clientId}")
    public ResponseEntity<UserProfile> show(@PathVariable int clientId) {
		Optional<UserProfile> userProfile = userProfileService.getById(clientId);

        return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public AuthResponse authenticate(@RequestBody AuthRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwtTokenUtil.generateToken(req.getUsername());
        return new AuthResponse(token);
    }
}
