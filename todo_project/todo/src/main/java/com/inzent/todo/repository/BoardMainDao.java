package com.inzent.todo.repository;

import java.util.List;

import com.inzent.todo.dto.DashBoardDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMainDao {
    
    @Autowired
    private SqlSession sqlSession;

    public List<DashBoardDto> getTodayList(){

        return sqlSession.selectList("dashboard.getTodayList");
    }
    
}