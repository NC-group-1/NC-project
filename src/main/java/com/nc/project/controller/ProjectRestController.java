package com.nc.project.controller;

import com.nc.project.model.Project;
import com.nc.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectRestController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void register(@RequestBody Project project){
        projectService.createProject(project);
    }
}
