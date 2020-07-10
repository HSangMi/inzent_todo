package com.inzent.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.DashBoardDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.BoardMainService;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class BoardMainController {

    @Autowired
    private BoardMainService boardMainService;

    // 오늘
    @Auth
    @GetMapping("/today")
    public List<DashBoardDto> getTodayList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();
        System.out.println("BoradMain Controller : get TodayList");
        List<DashBoardDto> list = boardMainService.getTodayList(userId);
        return list;
    }

    // 주간
    @Auth
    @GetMapping("/week")
    public List<DashBoardDto> getWeekList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        System.out.println("BoradMain Controller : get WeekList");
        List<DashBoardDto> list = boardMainService.getWeekList(userId);

        return list;
    }

    // 관심
    @Auth
    @GetMapping("/starred")
    public List<DashBoardDto> getStarredList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        System.out.println("BoradMain Controller : get InterestList");
        List<DashBoardDto> list = boardMainService.getStarredList(userId);

        return list;
    }

}