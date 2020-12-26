package com.nc.project.service.project.impl;

import com.nc.project.dao.project.ProjectDao;
import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.dto.TestCaseDetailsDto;
import com.nc.project.model.Project;
import com.nc.project.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (orderBy.equals(""))
            orderBy = "project_id";
        if (!order.equals("DESC")) {
            order = "";
        }

        return new Page<>(projectDao.getAllByPage(page, size, filter, orderBy, order), projectDao.getSizeOfResultSet(filter));
    }

    @Override
    public String getProjectName(int project_id) {
        return projectDao.getName(project_id);
    }

    @Override
    public void editProject(Project project) {
        projectDao.edit(project);
    }

    @Override
    public Optional<String> addParamsToEmail(TestCaseDetailsDto params, String pathToEmail) {
        File file = new File(pathToEmail);

        StringBuilder htmlStringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> paramsDetail = new ArrayList<>();
            while (reader.ready()) {
                htmlStringBuilder.append(reader.readLine());
            }

            paramsDetail.add(
                    (params.getProject().getName() != null ? "<p>Project name: " + params.getProject().getName() + "</p>" : "")
                            + (params.getProject().getLink() != null ? "<p>Project link: " + params.getProject().getLink() + "</p>" : "")
            );

            paramsDetail.add(
                    (params.getName() != null ? "<p>Test Case name: " + params.getName() + "</p>" : "")
                            + (params.getCreator() != null ? "<p>Test Case creator: " + params.getCreator().getName() + " " + params.getCreator().getSurname() + "</p>" : "")
                            + (params.getStarter() != null ? "<p>Test Case starter: " + params.getStarter().getName() + " " + params.getStarter().getSurname() + "</p>" : "")
                            + (params.getStartDate() != null ? "<p>Test Case started date: " + params.getStartDate() + "</p>" : "")
                            + (params.getFinishDate() != null ? "<p>Test Case finished date: " + params.getFinishDate() + "</p>" : "")
                            + (params.getStatus() != null ? "<p>Test Case status: " + params.getStatus() + "</p>" : "")
            );

            paramsDetail.add(
                    params.getActionInstRunDtos().stream()
                            .map(actionInstDto ->
                                            (actionInstDto.getActionName() != null ? "<p>Action : " + actionInstDto.getActionName() + " <b>have result:</b> " + actionInstDto.getResult() + "</p>" : "")
//                                            + (actionInstDto.getCompoundName() != null ? "<p>Compound result: " + actionInstDto.getCompoundName() + " <b>have result:</b> " + actionInstDto.getResult() + "</p>" : "")
                            )
                            .collect(Collectors.joining())
            );
            return Optional.of(String.format(htmlStringBuilder.toString(), String.join("", paramsDetail)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
