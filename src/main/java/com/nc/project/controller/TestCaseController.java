package com.nc.project.controller;

import com.nc.project.dto.ActionInstDto;
import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.ActionInst;
import com.nc.project.model.TestCase;
import com.nc.project.service.actionInst.ActionInstService;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-case")
public class TestCaseController {
    private final TestCaseService testCaseService;
    private final ActionInstService actionInstService;

    public TestCaseController(TestCaseService testCaseService, ActionInstService actionInstService) {
        this.testCaseService = testCaseService;
        this.actionInstService = actionInstService;
    }

    @PostMapping
    public ResponseEntity<TestCase> create(@RequestBody TestScenarioDto testScenarioDto) {
        TestCase createdTestCase = testCaseService.create(testScenarioDto);
        return new ResponseEntity<>(createdTestCase, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<ActionInst>> save(@RequestBody List<ActionInstDto> actionInstDto) {
        List<ActionInst> updatedInstances = actionInstService.update(actionInstDto);
        return new ResponseEntity<>(updatedInstances, HttpStatus.OK);
    }

    @GetMapping("/get_test_case_list/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<TestCaseDto>> getAll(
            @PathVariable int pageSize,
            @PathVariable int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    )
    {
        Page<TestCaseDto> testCaseList = testCaseService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);

        return new ResponseEntity<>(testCaseList, HttpStatus.OK);
    }

//    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.OK)
//    public void editTestCaseByName(@RequestBody TestCase testCase) {
//        testCaseService.editTestCase(testCase);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTestCase(@PathVariable int id) {
        testCaseService.deleteTestCase(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
