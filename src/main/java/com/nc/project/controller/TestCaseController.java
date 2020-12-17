package com.nc.project.controller;

import com.nc.project.dto.*;
import com.nc.project.model.TestCase;
import com.nc.project.service.runTestCase.RunTestCaseService;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test-case")
public class TestCaseController {
    private final TestCaseService testCaseService;
    private final RunTestCaseService runTestCaseService;

    public TestCaseController(TestCaseService testCaseService,
                              RunTestCaseService runTestCaseService) {
        this.testCaseService = testCaseService;
        this.runTestCaseService = runTestCaseService;
    }

    @GetMapping("/user/{userId}")
    public List<Integer> getUserTestCases(@PathVariable Integer userId){
        return this.testCaseService.getTestCasesIdByWatcher(userId);
    }

    @PostMapping
    public ResponseEntity<TestCase> create(@RequestBody TestScenarioDto testScenarioDto) {
        TestCase createdTestCase = testCaseService.create(testScenarioDto);
        return new ResponseEntity<>(createdTestCase, HttpStatus.CREATED);
    }
    @PutMapping
    public Boolean editTestCaseActions(@RequestBody TestScenarioDto testScenarioDto){
        return testCaseService.editTestCaseActions(testScenarioDto);
    }

    @GetMapping("/{idTestCase}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable Integer idTestCase) {
        Optional<TestCase> testCase = testCaseService.findById(idTestCase);
        return testCase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/run")
    public ResponseEntity<HttpStatus> runTestCase(@PathVariable int id,
                                      @RequestParam(name = "startedById") Integer startedById) {
        int status = runTestCaseService.runTestCase(id, startedById);
        if(status == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("actions/{id}")
    public ResponseEntity<List<ActionInstResponseDto>> getAllInstances(@PathVariable Integer id) {
        List<ActionInstResponseDto> instancesResponse = testCaseService.getAllInstances(id);
        return new ResponseEntity<>(instancesResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TestCaseDto>> getAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    )
    {
        Page<TestCaseDto> testCaseList = testCaseService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);

        return new ResponseEntity<>(testCaseList, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "edit")
    @ResponseStatus(value = HttpStatus.OK)
    public void editTestCaseByName(@RequestBody TestCase testCase) {
        testCaseService.editTestCase(testCase);
    }

    @DeleteMapping("/{test_case_id}")
    public ResponseEntity deleteTestCase(@PathVariable int test_case_id) {
        testCaseService.deleteTestCase(test_case_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
