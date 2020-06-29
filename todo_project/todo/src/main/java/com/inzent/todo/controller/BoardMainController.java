package com.inzent.todo.controller;

import java.util.List;

import com.inzent.todo.dto.DashBoardDto;
import com.inzent.todo.service.BoardMainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardMainController {
    
    @Autowired
    private BoardMainService boardMainService;

    public List<DashBoardDto> getTodayList(){

        List<DashBoardDto> list = boardMainService.getTodayList();

        return list;
    }


}