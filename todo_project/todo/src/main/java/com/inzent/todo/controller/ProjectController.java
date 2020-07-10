package com.inzent.todo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.MemberDto;
import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.dto.TaskBoardListDto;
import com.inzent.todo.dto.TaskDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ProjectService;
import com.inzent.todo.vo.FileVo;
import com.inzent.todo.vo.LabelVo;
import com.inzent.todo.vo.ProjectVo;
import com.inzent.todo.vo.SuperTaskVo;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Auth
    @GetMapping("/{pid}")
    public Map<String, Object> getProjectInfo(HttpServletRequest req, @PathVariable("pid") String pid) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();
        System.out.println("Project Controller : getProjectInfo : " + pid);
        // 멤버가 아니라면 못들어가게 막아야하지않나.?
        int memberNo = projectService.getMemberNo(pid, userId);
        System.out.println("현재 사용자의 프로젝트 MEMBER NUM : " + memberNo);

        // 이 프로젝트에 속한 업무(대) 중 private이면서, 내 id와 같은 업무 가져오기..
        List<TaskBoardListDto> taskBoardList = projectService.getTaskList(pid, memberNo);
        ProjectVo projectVo = projectService.getProject(pid);
        List<LabelVo> lableList = projectService.getLabelList(pid);
        List<MemberDto> memberList = projectService.getMemberList(pid);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("project", projectVo);
        map.put("memberNo", memberNo);
        map.put("memberList", memberList);
        map.put("taskBoardList", taskBoardList);
        map.put("labelList", lableList);
        // map.put("memberList", )
        return map;
    }

    @Auth
    @PostMapping("/addProject")
    public ProjectVo addProject(HttpServletRequest req, ProjectDto projectDto) throws Exception {
        System.out.println("Project Controller : addProject");
        UserVo user = (UserVo) req.getAttribute("user");
        return projectService.addProject(projectDto, user.getId());

        // System.out.println(projectDto.getCoverImg());
        // System.out.println(formObject.toString());
    }

    @PostMapping("/addSuperTask")
    public void addSuperTask(TaskDto taskDto, HttpServletRequest req) throws Exception {
        System.out.println("Project Controller : addSuperTask");
        projectService.addTask(taskDto);
        System.out.println(taskDto.toString());
        // System.out.println(projectDto.getCoverImg());
    }

    @PostMapping("/addSubTask")
    public void addSubTask(TaskDto taskDto, HttpServletRequest req) throws Exception {
        System.out.println("Project Controller : addSubTask");
        projectService.addTask(taskDto);
        // System.out.println(projectDto.getCoverImg());
        // System.out.println(formObject.toString());
    }

    @PostMapping("/addNewLabel")
    public void addNewLabel(@RequestBody LabelVo newLabel) {
        System.out.println("Project Controller : addNewLabel");
        System.out.println(newLabel.toString());
        projectService.addNewLabel(newLabel);
    }

    @PostMapping("/reorderTask")
    public List<TaskBoardListDto> reorderTask(@RequestBody TaskDto targetTask) {
        System.out.println("Project Controller : reorderTask");
        System.out.println(targetTask.toString());
        projectService.reorderTask(targetTask);
        // List<TaskBoardListDto> taskBoardList =
        return projectService.getTaskList(targetTask.getProjectId(), targetTask.getMemberNo());
        // memberNo);

    }

    @GetMapping("/getLabels/{pid}")
    public List<LabelVo> getLabel(@PathVariable("pid") String pid) {
        List<LabelVo> lableList = projectService.getLabelList(pid);
        return lableList;
    }

    @GetMapping("/getTask/{taskId}")
    public Map<String, Object> getTask(@PathVariable("taskId") String taskId) {
        TaskDto task = projectService.getTask(taskId);
        List<FileVo> files = projectService.getFiles(taskId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("task", task);
        map.put("files", files);

        return map;
    }
}