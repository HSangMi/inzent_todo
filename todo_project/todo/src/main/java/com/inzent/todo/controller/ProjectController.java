package com.inzent.todo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inzent.todo.dto.MemberDto;
import com.inzent.todo.dto.ProjectCardDto;
import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.dto.TaskBoardListDto;
import com.inzent.todo.dto.TaskDto;
import com.inzent.todo.dto.TaskUpdateDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ProjectService;
import com.inzent.todo.vo.CommentVo;
import com.inzent.todo.vo.FileVo;
import com.inzent.todo.vo.LabelVo;
import com.inzent.todo.vo.ProjectVo;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<ProjectCardDto> getProjectList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        System.out.println("Project Controller : get ProjectList");
        System.out.println(user.toString());

        return projectService.getProjectList(user.getId());
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

    @PostMapping("/updateTask")
    public void updateTask(TaskUpdateDto taskDto, HttpServletRequest req) throws Exception {
        System.out.println("Project Controller : updateTask");
        // System.out.println(taskDto.getLabels().length());
        projectService.updateTask(taskDto);
        // System.out.println(projectDto.getCoverImg());
        // System.out.println(taskDto.toString());
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

    @PostMapping("/addComment")
    public List<CommentVo> addComment(@RequestBody CommentVo comment) {
        System.out.println("Project Controller : addComment");
        System.out.println(comment.toString());
        projectService.addComment(comment);
        return projectService.getComments(comment.getTaskId());
        // projectService.reorderTask(targetTask);
        // List<TaskBoardListDto> taskBoardList =
        // return projectService.getTaskList(targetTask.getProjectId(),
        // targetTask.getMemberNo());
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
        List<CommentVo> comments = projectService.getComments(taskId);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("task", task);
        map.put("files", files);
        map.put("comments", comments);

        return map;
    }

    @PostMapping("/download")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response, @RequestBody FileVo f) {
        System.out.println("파일 다운");
        System.out.println(f.toString());

        try {
            File file = new File("./upload/files", f.getSaveName());
            Files.copy(file.toPath(), response.getOutputStream());
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            String contentDisposition = String.format("attachment; filename=%s", file.getName());
            int fileSize = Long.valueOf(file.length()).intValue();

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", contentDisposition);
            response.setContentLength(fileSize);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ServletContext context = request.getServletContext();
        // 서버에 저장되는 기본 경로
        // System.out.println("context:" + context);
        // String uploadPath = context.getRealPath("/upload");
        // System.out.println("uploadPath:" + uploadPath);
        // path : 파일 저장된 경로
        // String path = file.getPath();
        // sName : 경로에 실제 존재하는 파일명
        // String sName = file.getSaveName();
        // // 파일을 읽기 위한 파일 객체 생성
        // File f = new File("./upload/files", sName);
        // // dName : 사용자 컴퓨터에 저장할 파일명
        // String dName = file.getOrgName();
        // // 다운로드할 이름을 주지 않은 경우 사용자 브라우져에 이미지 출력
        // if (dName == null) {
        // response.setHeader("Content-Type", "image/jpg");
        // }
        // // 다운로드
        // else {

        // response.setHeader("Content-Type", "application/octet-stream");
        // // 한글이름으로 다운로드 처리
        // dName = new String(dName.getBytes("utf-8"), "8859_1");
        // response.setHeader("Content-Disposition", "attachment;filename=" + dName);
        // response.setHeader("Content-Transfer-Encoding", "binary");
        // response.setHeader("Content-Length", String.valueOf(f.length()));
        // }

        // // 특정 위치의 파일을 읽어서 사용자 브라우져로 출력
        // FileInputStream fis = new FileInputStream(f);
        // BufferedInputStream bis = new BufferedInputStream(fis);

        // OutputStream out = response.getOutputStream();
        // BufferedOutputStream bos = new BufferedOutputStream(out);

        // while (true) {
        // int ch = bis.read();
        // if (ch == -1)
        // break;

        // bos.write(ch);
        // }
        // bis.close();
        // fis.close();

        // bos.close();
        // out.close();

    }

}