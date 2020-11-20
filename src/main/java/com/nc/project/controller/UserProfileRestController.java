package com.nc.project.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.TextNode;
import com.nc.project.model.UserProfile;
import com.nc.project.service.ProfileService;

@RestController
public class UserProfileRestController {
	private ProfileService userProfileService;

	@Autowired
	UserProfileRestController(ProfileService userProfileService) {
		this.userProfileService = userProfileService;
	}

	@GetMapping("user/{clientId}")
	public ResponseEntity<UserProfile> show(@PathVariable int clientId) {
		Optional<UserProfile> userProfile = userProfileService.getById(clientId);

		return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("my_profile")
	public ResponseEntity<UserProfile> show(@RequestBody String email) {
		Optional<UserProfile> userProfile = userProfileService.getByEmail(email);
		return userProfile.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
