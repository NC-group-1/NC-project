package com.nc.project.controller;

import com.nc.project.dto.UserProfileDto;
import com.nc.project.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/list")
public class UsersListRestController {

    private final UserService userService;

    public UsersListRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size)
    {
        List<UserProfileDto> userList = userService.getAllByPage(page, size);
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
