package com.nc.project.service.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.model.Project;
import com.nc.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project createProject(Project project) {
        projectDao.create(project);
        return project;
    }
}
