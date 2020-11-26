package com.nc.project.service;

import com.nc.project.model.Project;

import java.util.List;

public interface ProjectService {
    void createProject(Project project);
    List<Project> getAll();
    void editProject(Project project);
    List<Project> getAllByPage(int page, int size);
    Integer getNumPages(int size);
}
