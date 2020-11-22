package com.nc.project.service.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.model.Project;
import com.nc.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public void createProject(Project project) {
        projectDao.create(project);
    }

    @Override
    public List<Project> getAll() {
        return projectDao.getAll();
    }

    @Override
    public void editProject(Project project) {
        projectDao.edit(project);
    }
}
