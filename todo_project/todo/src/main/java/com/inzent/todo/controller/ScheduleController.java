package com.inzent.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ScheduleService;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;

    @Auth
    @GetMapping("/calendar")
    public List<ScheduleDto> getCalendatList(HttpServletRequest req){
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        List<ScheduleDto> list  = scheduleService.getCalendatList(userId);
        System.out.println("여기까지 도착?!");
        return list;
    }


}