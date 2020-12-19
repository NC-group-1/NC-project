package com.nc.project.controller;

import com.nc.project.dto.*;
import com.nc.project.model.TestCase;
import com.nc.project.model.Watcher;
import com.nc.project.service.runTestCase.RunTestCaseService;
import com.nc.project.service.testCase.TestCaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/{id}/schedule")
    public ResponseEntity<HttpStatus> scheduleTestCase(@PathVariable int id,
                                                  @RequestParam(name = "startedById") Integer startedById) {
        int status = runTestCaseService.scheduleTestCase(id, startedById);
        if(status == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}/stop")
    public ResponseEntity<HttpStatus> stopTestCase(@PathVariable int id) {
        int status = runTestCaseService.suspendTestCase(id);
        if(status == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}/resume")
    public ResponseEntity<HttpStatus> resumeTestCase(@PathVariable int id) {
        int status = runTestCaseService.resumeTestCase(id);
        if(status == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<HttpStatus> cancelTestCase(@PathVariable int id) {
        int status = runTestCaseService.interruptTestCase(id);
        if(status == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @MessageMapping("/actionInst/tc")
    public void getTestCaseActionInstances(Integer testCaseId) {
        this.runTestCaseService.sendActionInstToTestCaseSocket(testCaseId);
    }

    @GetMapping("/{id}/run-details")
    public ResponseEntity<List<ActionInstRunDto>> getAllActionInstRunDtos(@PathVariable Integer id) {
        List<ActionInstRunDto> actionInstRunDtos = testCaseService.getAllActionInstRunDtos(id);
        return new ResponseEntity<>(actionInstRunDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<TestCaseDetailsDto> getTestCaseDetailsById(@PathVariable Integer id) {
        Optional<TestCaseDetailsDto> testCase = testCaseService.getTestCaseDetailsById(id);
        return testCase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("actions/{id}")
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

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getListWatcherByTestCaseId(@RequestParam int test_case_id) {
        List<UserProfileDto> watchers = testCaseService.getListWatcherByTestCaseId(test_case_id);
        return new ResponseEntity<>(watchers,HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserProfileDto>> getUsersByName(@RequestParam String name) {
        List<UserProfileDto> users = testCaseService.getUsersByName(name);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @PostMapping("/add-watcher")
    @ResponseStatus(value = HttpStatus.OK)
    public void addWatcher(@RequestBody Watcher watcher) {
        testCaseService.addWatcher(watcher);
    }


    @GetMapping("/historyList/{projectId}")
    public ResponseEntity<Page<TestCaseHistory>> getHistory(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order,
            @PathVariable int projectId
    )
    {
        Page<TestCaseHistory> testCaseList = testCaseService.getHistory(pageIndex, pageSize,filter,orderBy,order,projectId);

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
