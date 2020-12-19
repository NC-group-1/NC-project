package com.nc.project.service.project;

import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;

import java.util.List;

public interface ProjectService {
    void createProject(Project project);
    Page<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order);
    String getProjectName(int project_id);
    void editProject(Project project);

}
