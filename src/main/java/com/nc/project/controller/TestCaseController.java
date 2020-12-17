package com.nc.project.controller;

import com.nc.project.dto.ActionInstResponseDto;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestCase;
import com.nc.project.service.runTestCase.RunTestCaseService;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ncp/test-case")
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

    @PostMapping("/{id}/run")
    public ResponseEntity runTestCase(@PathVariable int id,
                                      @RequestParam(name = "startedById") Integer startedById) {
        runTestCaseService.runTestCase(id, startedById);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ActionInstResponseDto>> getAllInstances(@PathVariable Integer id) {
        List<ActionInstResponseDto> instancesResponse = testCaseService.getAllInstances(id);
        return new ResponseEntity<>(instancesResponse, HttpStatus.OK);
    }

    @GetMapping("/list/{projectId}")
    public ResponseEntity<Page<TestCaseDto>> getAll(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order,
            @PathVariable int projectId
    )
    {
        Page<TestCaseDto> testCaseList = testCaseService.getAllByPage(pageIndex, pageSize,filter,orderBy,order,projectId);

        return new ResponseEntity<>(testCaseList, HttpStatus.OK);
    }


    @PutMapping(value="/edit",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void editTestCaseByName(@RequestBody TestCase testCase) {
        testCaseService.updateTestCase(testCase);
    }

    @DeleteMapping("/{test_case_id}")
    public ResponseEntity deleteTestCase(@PathVariable int test_case_id) {
        testCaseService.deleteTestCase(test_case_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
