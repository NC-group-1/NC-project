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

//    @GetMapping
//    public ResponseEntity<List<Project>> getAll() {
//        List<Project> projectList = projectService.getAll();
//        return new ResponseEntity<>(projectList, HttpStatus.OK);
//    }

    @PutMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody Project project) {
        projectService.editProject(project);
    }

    @GetMapping("/get_project_list/{page}/{size}")
    public ResponseEntity<List<Project>> getPaginatedProjects(
            @PathVariable int page,
            @PathVariable int size)
    {
        System.out.println(page + " " + size);
        List<Project> projectList = projectService.getAllByPage(page, size);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @GetMapping("/get_number/{size}")
    public ResponseEntity<Integer> getNumberOfProjects(@PathVariable int size)
    {
        Integer numPages = projectService.getNumPages(size);
        return new ResponseEntity<>(numPages, HttpStatus.OK);
    }

}
