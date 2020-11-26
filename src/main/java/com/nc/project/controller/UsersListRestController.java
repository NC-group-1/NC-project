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
@RequestMapping("user/")
public class UsersListRestController {

    private final UserService userService;

    public UsersListRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("get_user_list/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<UserProfileDto>> getAll(
            @PathVariable int pageSize,
            @PathVariable int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
            )
    {

        Page<UserProfileDto> userList = userService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);

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
