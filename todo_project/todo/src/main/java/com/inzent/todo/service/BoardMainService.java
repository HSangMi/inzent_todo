package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.DashBoardDto;
import com.inzent.todo.repository.BoardMainDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardMainService {
    
    @Autowired
    private BoardMainDao boardMainDao;

    public List<DashBoardDto> getTodayList(){

        return boardMainDao.getTodayList();
    }
}