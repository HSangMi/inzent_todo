package com.inzent.todo.service;

import java.util.ArrayList;
import java.util.List;

import com.inzent.todo.dto.DashBoardDto;
import com.inzent.todo.repository.BoardMainDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardMainService {

    @Autowired
    private BoardMainDao boardMainDao;

    int pcount = 0;
    int hcount = 0;
    int ccount = 0;
    int wcount = 0;
    int ecount = 0;

    // 오늘 할일 목록
    public List<DashBoardDto> getTodayList(String userId) {
        System.out.println("BoardMain Service : get TodayList");
        return boardMainDao.getTodayList(userId);
    }

    // 주간 할일 목록
    public List<DashBoardDto> getWeekList(String userId) {
        System.out.println("BoardMain Service : get WeekList");
        return boardMainDao.getWeekList(userId);
    }

    // 관심 할일 목록
    public List<DashBoardDto> getStarredList(String userId) {
        System.out.println("BoardMain Service : get StaredList");
        return boardMainDao.getStarredList(userId);
    }
}
