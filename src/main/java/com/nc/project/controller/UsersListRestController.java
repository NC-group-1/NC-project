package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.PasswordDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/list")
public class UsersListRestController {

    private final UserService userService;

    public UsersListRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileDto>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
            )
    {

        Page<UserProfileDto> userList = userService.getAllByPage(page, size,filter,orderBy,order);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void update(
            @RequestBody UserProfileDto userProfileDto)
    {
        userService.UpdateUserFromTable(userProfileDto);
    }
}
