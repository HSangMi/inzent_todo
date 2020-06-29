package com.inzent.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ProjectService;
import com.inzent.todo.vo.ProjectVo;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Auth
    @GetMapping("/")
    public List<ProjectVo> getProjectList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        System.out.println("Project Controller : get ProjectList");
        System.out.println(user.toString());

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