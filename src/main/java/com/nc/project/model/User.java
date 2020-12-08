package com.nc.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private int id;
    private String name;
    private String surname;
    private String email;
    private String role;
    private Boolean activated;
    private String imageLink;
    private String pass;
    private Timestamp regDate;
    private String aboutMe;
    private String emailCode;
    private Timestamp codeExpireDate;

    public User(String email, String role, Boolean activated, String pass) {
        this.email = email;
        this.role = role;
        this.activated = activated;
        this.pass = pass;
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String email, String emailCode, Timestamp codeExpireDate) {
        this.id = id;
        this.email = email;
        this.emailCode = emailCode;
        this.codeExpireDate = codeExpireDate;
    }
    public User(int id, String email, String name,String surname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() { return pass; }

    @Override
    public String getUsername() { return email; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return true; }

}
