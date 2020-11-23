package com.nc.project.controller;

import com.nc.project.authentification.JwtTokenUtil;
import com.nc.project.dto.AuthRequest;
import com.nc.project.dto.AuthResponse;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.User;
import com.nc.project.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public UserRestController(UserService userService, JwtTokenUtil jwtTokenUtil,
                              AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody User user){
        userService.createUser(user);
    }
    
    @GetMapping("{email}")
    public ResponseEntity<UserProfileDto> findUserByEmail(@PathVariable String email) {
		Optional<UserProfileDto> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public AuthResponse authenticate(@RequestBody AuthRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwtTokenUtil.generateToken(req.getUsername());
        return new AuthResponse(token);
    }
}
