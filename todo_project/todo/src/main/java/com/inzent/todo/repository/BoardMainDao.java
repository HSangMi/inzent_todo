package com.inzent.todo.repository;

import java.util.ArrayList;
import java.util.List;

import com.inzent.todo.dto.DashBoardDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMainDao {

    @Autowired
    private SqlSession sqlSession;

    public List<DashBoardDto> getTodayList(String userId) {
        System.out.println("BoardMain Dao : get TodayList");

        return sqlSession.selectList("dashboard.getTodayList", userId);
    }

    public List<DashBoardDto> getWeekList(String userId) {
        System.out.println("BoardMain Dao : get WeekList");

        return sqlSession.selectList("dashboard.getWeekList", userId);
    }

    public List<DashBoardDto> getStarredList(String userId) {
        System.out.println("BoardMain Dao : get StarredList");
        return sqlSession.selectList("dashboard.getStarredList", userId);
    }

}