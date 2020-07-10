package com.inzent.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inzent.todo.dto.MemberDto;
import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.dto.TaskBoardListDto;
import com.inzent.todo.dto.TaskDto;
import com.inzent.todo.repository.FileDao;
import com.inzent.todo.repository.MemberDao;
import com.inzent.todo.repository.ProjectDao;
import com.inzent.todo.util.DBUtil;
import com.inzent.todo.util.FileUtil;
import com.inzent.todo.vo.FileVo;
import com.inzent.todo.vo.ImageVo;
import com.inzent.todo.vo.LabelVo;
import com.inzent.todo.vo.MemberVo;
import com.inzent.todo.vo.ProjectVo;
import com.inzent.todo.vo.SuperTaskVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private MemberDao memberDao;

    public ProjectVo addProject(ProjectDto projectDto, String userId) throws Exception {
        System.out.println("ProjectService.addproject ---");
        System.out.println(projectDto.toString());
        System.out.println(projectDto.getMembers().size());
        System.out.println(projectDto.getMembers().isEmpty());
        ProjectVo projectVo = new ProjectVo();
        String projectId = DBUtil.generateKey("PJ");
        projectVo.setId(projectId);
        projectVo.setTitle(projectDto.getTitle());
        projectVo.setDescription(projectDto.getDescription());
        projectVo.setUsePublic(projectDto.getUsePublic());
        projectVo.setStartDate(projectDto.getStartDate());
        projectVo.setEndDate(projectDto.getEndDate());
        // 프로젝트 테이블에 insert
        // projectVo에 Dto내용 뽑아서 담기!

        // TODO 프로젝트 이미지가 있다면 저장, 없다면,색깔? 테마?
        if (projectDto.getCoverImg() == null) {
            System.out.println("CoverImg is Empty..");
            projectVo.setImgNo("#" + projectDto.getCoverColor());
        } else {
            System.out.println("CoverImg !");
            MultipartFile imgFile = projectDto.getCoverImg();

            String orgFileName = imgFile.getOriginalFilename();
            String fileExtName = orgFileName.substring(orgFileName.lastIndexOf('.') + 1, orgFileName.length());
            String saveFileName = DBUtil.generateSaveFileName(fileExtName);

            // 파일 업로드
            FileUtil.writeImgFile(imgFile, saveFileName);

            // DB에 img 저장
            ImageVo imageVo = new ImageVo();
            imageVo.setSaveName(saveFileName);
            int img_key = fileDao.insertImg(imageVo);
            projectVo.setImgNo(img_key + "");
        }

        // TODO 프로젝트 멤버테이블에 생성한사람, 멤버가 있다면 멤버도 추가
        MemberVo mem = new MemberVo();
        mem.setProjectId(projectId);
        // 프로젝트 생성자 등록
        mem.setUserId(userId);
        projectVo.setManager(memberDao.insertMember(mem));

        if (projectDto.getMembers().isEmpty()) {
            System.out.println("멤버 아직");
        } else {
            System.out.println("멤버가 있지롱");
            // 여러명등록해줘야해.ㅠㅠ
        }
        projectDao.insertProject(projectVo);

        return projectVo;
    }

    public void addTask(TaskDto taskDto) throws Exception {
        System.out.println("-----------ProjectService.addTask ----------");
        System.out.println(taskDto.toString());
        System.out.println("--------------------------------------------");

        if (taskDto.getSortNo() == 0) {
            taskDto.setSortNo(65535);
        }
        String taskId = "";
        if (taskDto.getTaskSuperId() == null) {
            System.out.println(" :: ADD SUPER TASK:: ");
            taskId = DBUtil.generateKey("TP");
            taskDto.setTaskId(taskId);
            projectDao.insertSuperTask(taskDto);
        } else {
            System.out.println(" :: ADD SUB TASK:: ");
            taskId = DBUtil.generateKey("TB");
            taskDto.setTaskId(taskId);
            projectDao.insertSubTask(taskDto);

        }

        // superTask.setTaskId(taskId);

        // 프로젝트 테이블에 insert
        // projectVo에 Dto내용 뽑아서 담기!

        if (taskDto.getAttachFiles() != null) {
            System.out.println("Attach files... ");
            for (MultipartFile f : taskDto.getAttachFiles()) {
                String orgFileName = f.getOriginalFilename();
                int size = (int) f.getSize();
                String fileExtName = orgFileName.substring(orgFileName.lastIndexOf('.') + 1, orgFileName.length());
                String saveFileName = DBUtil.generateSaveFileName(fileExtName);
                // 파일 업로드
                FileUtil.writeFile(f, saveFileName);
                // DB에 img 저장
                FileVo file = new FileVo();
                file.setSaveName(saveFileName);
                file.setOrgName(orgFileName);
                file.setExt(fileExtName);
                file.setSize(size);
                file.setTaskId(taskId);
                fileDao.insertFile(file);
            }
        }

        // 담당자가 있다면, 담당자 테이블에 추가!
        // MemberVo mem = new MemberVo();
        // mem.setProjectId(projectId);
        // // 프로젝트 생성자 등록
        // mem.setUserId("userTestId");
        // projectVo.setManager(memberDao.insertMember(mem));

        // if (projectDto.getMembers().isEmpty()) {
        // System.out.println("멤버 아직");
        // } else {
        // System.out.println("멤버가 있지롱");
        // // 여러명등록해줘야해.ㅠㅠ
        // }

    }

    public List<ProjectVo> getProjectList() {
        return projectDao.selectProjectList();
    }

    public List<TaskBoardListDto> getTaskList(String pid, int memNo) {
        // System.out.println("UserID : " + userId);
        List<TaskBoardListDto> taskBoardList = new ArrayList<>();

        List<SuperTaskVo> superTaskList = projectDao.selectTaskList(pid, memNo);
        System.out.println("superTaskList size : " + superTaskList.size());
        System.out.println("--------------------------------------------");
        for (SuperTaskVo superTask : superTaskList) {
            TaskBoardListDto tbl = new TaskBoardListDto();
            tbl.setSuperTask(superTask);
            List<TaskDto> subTaskList = projectDao.selectTaskSubList(superTask.getTaskId(), memNo);
            if (subTaskList == null) {
                System.out.println("서브태스크가 없습니다.");
            } else {
                // System.out.println("서브테스크가 있습니다.");
                // System.out.println(subTaskList.toString());
                tbl.setSubTaskList(subTaskList);
            }
            taskBoardList.add(tbl);
        }

        return taskBoardList;
    }

    public ProjectVo getProject(String pid) {
        return projectDao.selectProject(pid);
    }

    public int getMemberNo(String pid, String userId) {
        return projectDao.selectMemberNo(pid, userId);
    }

    public void addNewLabel(LabelVo label) {
        projectDao.insertNewLabel(label);
    }

    public List<LabelVo> getLabelList(String pid) {
        return projectDao.getLabelList(pid);
    }

    public List<MemberDto> getMemberList(String pid) {
        return projectDao.getMemberList(pid);
    }

    public TaskDto getTask(String taskId) {
        return projectDao.getTask(taskId);
    }

    public List<FileVo> getFiles(String taskId) {
        return projectDao.getFiles(taskId);
    }

    public void reorderTask(TaskDto targetTask) {
        projectDao.updateSortNo(targetTask);
    }

}