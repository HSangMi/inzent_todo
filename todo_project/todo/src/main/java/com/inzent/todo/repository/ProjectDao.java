package com.inzent.todo.repository;

import java.util.List;

import com.inzent.todo.vo.ProjectVo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDao {

    @Autowired
    private SqlSession sqlSession;

    public int insertProject(ProjectVo projectVo) {

        System.out.println("ProjectDao - insertProject");

        System.out.println(projectVo.toString());
        return sqlSession.insert("project.insertProject", projectVo);

    }

    public List<ProjectVo> selectProjectList() {

        return sqlSession.selectList("project.selectProjectList");
    }

}