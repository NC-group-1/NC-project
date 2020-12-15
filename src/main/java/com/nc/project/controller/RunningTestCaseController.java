package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.RunningTestCaseDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.*;
import com.nc.project.service.runningTestCase.RunningTestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/running-test-case")
public class RunningTestCaseController {
    @Autowired
    private RunningTestCaseService runningTestCaseService;

    @GetMapping("/list")
    public ResponseEntity<Page<RunningTestCaseDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    )
    {
        Page<RunningTestCaseDto> runningTestCaseList = runningTestCaseService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);

        return new ResponseEntity<>(runningTestCaseList, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void editRunningTestCaseByID(@RequestBody TestCase testCase) {
        runningTestCaseService.editRunningTestCase(testCase);
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileDto>> getWatcherByTestCaseId(@RequestParam int test_case_id) {
        Page<UserProfileDto> watchers = runningTestCaseService.getWatcherByTestCaseId(test_case_id);
        return new ResponseEntity<>(watchers,HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserProfileDto>> getUsersByName(@RequestParam String name) {
        Page<UserProfileDto> users = runningTestCaseService.getUsersByName(name);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/add-watcher")
    @ResponseStatus(value = HttpStatus.OK)
    public void addWatcher(@RequestBody Watcher watcher) {
        runningTestCaseService.addWatcher(watcher);
    }

}