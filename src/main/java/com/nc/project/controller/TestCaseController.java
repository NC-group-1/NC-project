package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestCaseDto;
import com.nc.project.model.TestCase;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test_case")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

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

    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE)
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