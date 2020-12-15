package com.nc.project.dao.project;

import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;

import java.util.List;

public interface ProjectDao {
    void create(Project project);

    List<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order);

    void edit(Project project);

    Integer getSizeOfResultSet(String filter);
}
