package com.nc.project.controller;


import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;
import com.nc.project.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("get_project_list/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<ProjectDto>> getAll(
            @PathVariable int pageSize,
            @PathVariable int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    )
    {

        Page<ProjectDto> projectList = projectService.getAllByPage(pageIndex, pageSize,filter,orderBy,order);

        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody Project project) {
        projectService.editProject(project);
    }



}
