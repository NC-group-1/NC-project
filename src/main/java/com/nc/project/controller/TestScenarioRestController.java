package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.TestScenarioDto;
import com.nc.project.model.TestScenario;
import com.nc.project.service.testScenario.TestScenarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ncp/test-scenario")
public class TestScenarioRestController {
    private final TestScenarioService testScenarioService;

    public TestScenarioRestController(TestScenarioService testScenarioService) {
        this.testScenarioService = testScenarioService;
    }

    @GetMapping("{id}")
    public TestScenario getTestScenarioById(@PathVariable int id) {
        return testScenarioService.getTestScenarioById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody TestScenario testScenario) {
        System.out.println(testScenario);
        testScenarioService.createTestScenario(testScenario);
    }

    @GetMapping("list/{projectId}")
    public ResponseEntity<Page<TestScenarioDto>> getAllByProject(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order,
            @PathVariable int projectId
    ) {
        Page<TestScenarioDto> testScenarioList = testScenarioService.getAllByPage(pageIndex, pageSize, filter, orderBy, order, projectId);
        return new ResponseEntity<>(testScenarioList, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<TestScenarioDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    ) {

        Page<TestScenarioDto> testScenarioList = testScenarioService.getAllByPage(pageIndex, pageSize, filter, orderBy, order, 0);
        return new ResponseEntity<>(testScenarioList, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody TestScenario testScenario) {
        testScenarioService.editTestScenario(testScenario);
    }

    @DeleteMapping("{testScenarioId}")
    public void deleteCompound(@PathVariable int testScenarioId) {
        testScenarioService.deleteTestScenario(testScenarioId);
    }
}
