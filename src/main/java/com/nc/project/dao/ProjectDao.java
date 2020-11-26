package com.nc.project.dao;

import com.nc.project.model.Project;

import java.util.List;

public interface ProjectDao {
    void create(Project project);
    List<Project> getAll();
    void edit(Project project);
    List<Project> getAllByPage(int page, int size);
    Integer getNumPages(int size);
}
