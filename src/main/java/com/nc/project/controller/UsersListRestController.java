package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ncp/users/")
public class UsersListRestController {

    private final UserService userService;

    public UsersListRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("list")
    public ResponseEntity<Page<UserProfileDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
            )
    {

        Page<UserProfileDto> userList = userService.getAllByPage(pageIndex,pageSize,filter,orderBy,order);

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void update(
            @RequestBody UserProfileDto userProfileDto)
    {
        userService.updateUserFromTable(userProfileDto);
    }
}
