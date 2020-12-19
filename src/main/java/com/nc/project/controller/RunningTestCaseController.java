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

import java.util.List;


@RestController
@RequestMapping("api/ncp/running-test-case")
public class RunningTestCaseController {
    @Autowired
    private RunningTestCaseService runningTestCaseService;

    @GetMapping("/list/{projectId}")
    public ResponseEntity<Page<RunningTestCaseDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order,
            @PathVariable int projectId
    )
    {
        Page<RunningTestCaseDto> runningTestCaseList = runningTestCaseService.getAllByPage(pageIndex, pageSize,filter,orderBy,order,projectId);

        return new ResponseEntity<>(runningTestCaseList, HttpStatus.OK);
    }

    @PutMapping(value="/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void editRunningTestCaseByID(@RequestBody TestCase testCase) {
        runningTestCaseService.editRunningTestCase(testCase);
    }

}