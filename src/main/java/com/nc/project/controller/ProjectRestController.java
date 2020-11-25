package com.nc.project.controller;


import com.nc.project.model.Project;
import com.nc.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectRestController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody Project project) {
        projectService.createProject(project);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projectList = projectService.getAll();
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody Project project) {
        projectService.editProject(project);
    }



}
