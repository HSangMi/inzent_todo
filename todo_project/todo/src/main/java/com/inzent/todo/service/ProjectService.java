package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.ProjectDto;
import com.inzent.todo.repository.FileDao;
import com.inzent.todo.repository.MemberDao;
import com.inzent.todo.repository.ProjectDao;
import com.inzent.todo.util.DBUtil;
import com.inzent.todo.util.FileUtil;
import com.inzent.todo.vo.ImageVo;
import com.inzent.todo.vo.MemberVo;
import com.inzent.todo.vo.ProjectVo;

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

    public ProjectVo addProject(ProjectDto projectDto) throws Exception {
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
        projectVo.setDueDate(projectDto.getDueDate());
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
            FileUtil.writeFile(imgFile, saveFileName);

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
        mem.setUserId("userTestId");
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

    public List<ProjectVo> getProjectList() {
        return projectDao.selectProjectList();
    }

}