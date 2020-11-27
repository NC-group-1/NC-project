package com.nc.project.service.impl;

import com.nc.project.dao.ProjectDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
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
    public Page<ProjectDto> getAllByPage(int page, int size, String filter, String orderBy, String order) {
        if(orderBy.equals(""))
            orderBy = "project_id";
        if(!order.equals("DESC")){
            order="";
        }

        return new Page(projectDao.getAllByPage(page,size,filter,orderBy,order),projectDao.getSizeOfResultSet(filter).get());
    }

    @Override
    public void editProject(Project project) {
        projectDao.edit(project);
    }


}
