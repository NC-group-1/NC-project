package com.nc.project.service.project;

import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.dto.TestCaseDetailsDto;
import com.nc.project.model.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    void createProject(Project project);
    Page<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    void editProject(Project project);
    Optional<String> addParamsToEmail(TestCaseDetailsDto params, String pathToEmail);
}
