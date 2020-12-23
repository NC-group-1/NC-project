package com.nc.project.controller;

import com.nc.project.authentification.JwtTokenUtil;
import com.nc.project.dto.AuthRequest;
import com.nc.project.dto.AuthResponse;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.User;
import com.nc.project.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.mail.MessagingException;
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
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody User user){
        System.out.println(user);
        userService.createUser(user);
    }

    @PutMapping
    public ResponseEntity<UserProfileDto> updateUserProfile(@RequestBody UserProfileDto user){
        Optional<UserProfileDto> userUpdated = userService.updatePersonalProfile(user);
        return userUpdated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<UserProfileDto> findUserByEmail(@PathVariable String email) throws InterruptedException {
        Optional<UserProfileDto> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @RolesAllowed("ADMIN")
    @GetMapping("{id}")
    public ResponseEntity<UserProfileDto> findUserById(@PathVariable int id) {
		Optional<UserProfileDto> user = userService.findUserProfileById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public AuthResponse authenticate(@RequestBody AuthRequest req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        String token = jwtTokenUtil.generateToken(req.getUsername(), userService.getUserRoleByEmail(req.getUsername()), userService.getUserIdByEmail(req.getUsername()));
        return new AuthResponse(token);
    }
}
