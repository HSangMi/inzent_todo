package com.inzent.todo.controller;

import java.util.List;

import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.service.ProjectService;
import com.inzent.todo.vo.ProjectVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public List<ProjectVo> getProjectList() {
        System.out.println("Project Controller : get ProjectList");

        return projectService.getProjectList();
        // return null;
    }

    @PostMapping("/addProject")
    public ProjectVo addProject(ProjectDto projectDto) throws Exception {
        System.out.println("Project Controller : addProject");

        return projectService.addProject(projectDto);

        // System.out.println(projectDto.getCoverImg());
        // System.out.println(formObject.toString());
    }

}