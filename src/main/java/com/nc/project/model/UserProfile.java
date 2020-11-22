package com.nc.project.model;

import java.util.Date;

import lombok.Data;

@Data
public class UserProfile {

	private String name;
	private String surname;
	private String role;
	private boolean activated;
	private Date regDate;
	private String aboutMe;
	private String imageLink;

	public UserProfile(String name, String surname, String role, Date regDate, boolean activated, String aboutMe,
			String imageLink) {
		this.name = name;
		this.surname = surname;
		this.role = role;
		this.regDate = regDate;
		this.activated = activated;
		this.aboutMe = aboutMe;
		this.imageLink = imageLink;
	}
}
