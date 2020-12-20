package com.nc.project.controller;

import com.nc.project.dto.Page;
import com.nc.project.dto.ProjectDto;
import com.nc.project.dto.ReportDto;
import com.nc.project.dto.TestCaseDetailsDto;
import com.nc.project.exception.UserNotFoundException;
import com.nc.project.model.Project;
import com.nc.project.service.mail.EmailSendReportService;
import com.nc.project.service.mail.EmailService;
import com.nc.project.service.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("api/ncp/project")
public class ProjectRestController {
    private String SUBJECT = "Report";
    private String PATH_TO_ATTACHMENT = "src/main/resources/email/send-report.html";

    private final ProjectService projectService;
    private final EmailSendReportService emailSendReportService;

    public ProjectRestController(ProjectService projectService, EmailSendReportService emailSendReportService) {
        this.projectService = projectService;
        this.emailSendReportService = emailSendReportService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void create(@RequestBody Project project) {
        projectService.createProject(project);
    }

    @GetMapping("list")
    public ResponseEntity<Page<ProjectDto>> getAll(
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "") String orderBy,
            @RequestParam(defaultValue = "") String order
    ) {

        Page<ProjectDto> projectList = projectService.getAllByPage(pageIndex, pageSize, filter, orderBy, order);
        return new ResponseEntity<>(projectList, HttpStatus.OK);
    }

    @PostMapping("send-report")
    public void sendReport(@RequestBody ReportDto reportDto) {
        System.out.println(reportDto);
        try {
            emailSendReportService.sendMessageWithAttachment(reportDto.getEmail(), SUBJECT, reportDto.getTestCaseDetailsDto(), PATH_TO_ATTACHMENT);
        } catch (MessagingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void editProjectByName(@RequestBody Project project) {
        projectService.editProject(project);
    }
}
