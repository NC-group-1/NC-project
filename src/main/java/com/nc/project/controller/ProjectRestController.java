package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;
import com.nc.project.service.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/project")
public class ProjectRestController {

    private final ProjectService projectService;

    public ProjectRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody Project project) {
        System.out.println(project);
        projectService.createProject(project);
    }

    @GetMapping("list")
    public ResponseEntity<Page<ProjectDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    )
    {

        Page<ProjectDto> projectList = projectService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody Project project) {
        projectService.editProject(project);
    }
}
