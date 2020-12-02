package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.dto.UserProfileDto;
import com.nc.project.model.Project;
import com.nc.project.model.TestScenario;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/test_scenario")
public class TestScenarioRestController {
    private final TestScenarioService testScenarioService;

    public TestScenarioRestController(TestScenarioService testScenarioService) {
        this.testScenarioService = testScenarioService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody TestScenario testScenario) {
        testScenarioService.createTestScenario(testScenario);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TestScenarioDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order,
            @RequestParam(defaultValue = "0") int projectId
    )
    {

        Page<TestScenarioDto> testScenarioList = testScenarioService.getAllByPage(pageIndex, pageSize,filter,orderBy,order,projectId);

        return new ResponseEntity<>(testScenarioList, HttpStatus.OK);
    }
    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody TestScenario testScenario) {
        testScenarioService.editTestScenario(testScenario);
    }
}
