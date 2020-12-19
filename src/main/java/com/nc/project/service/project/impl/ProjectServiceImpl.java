package com.nc.project.service.project.impl;

import com.nc.project.dao.project.ProjectDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.model.Project;
import com.nc.project.service.project.ProjectService;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void createProject(Project project) {
        projectDao.create(project);
    }

    @Override
    public Page<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        if(orderBy.equals(""))
            orderBy = "project_id";
        if(!order.equals("DESC")){
            order="";
        }

        return new Page<>(projectDao.getAllByPage(page,size,filter,orderBy,order),projectDao.getSizeOfResultSet(filter));
    }

    @Override
    public String getProjectName(int project_id) {
        return projectDao.getName(project_id);
    }

    @Override
    public void editProject(Project project) {
        projectDao.edit(project);
    }


}
