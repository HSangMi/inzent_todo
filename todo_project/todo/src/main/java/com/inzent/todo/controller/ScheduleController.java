package com.inzent.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.inzent.todo.dto.CalDateDetailDto;
import com.inzent.todo.dto.CalFilterItemDto;
import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
import com.inzent.todo.dto.ClickDateDto;
import com.inzent.todo.dto.FilterDto;
import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.security.Auth;
import com.inzent.todo.service.ScheduleService;
import com.inzent.todo.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Auth
    @GetMapping("/calendar")
    public List<ScheduleDto> getCalendarList(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        List<ScheduleDto> list = scheduleService.getCalendarList(userId);
        return list;
    }

    // 일정 등록 - 선택된 프로젝트에 관련된 업무대 출력
    @Auth
    @PostMapping("/chkproject")
    public List<ChkSuperTasksDto> getSuperTasks(@RequestBody ChkProjectDto chkprjdto, HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        chkprjdto.setId(userId);

        List<ChkSuperTasksDto> list = scheduleService.getSuperTasks(chkprjdto);
        return list;
    }

    // 해당날짜 업무 조회
    @Auth
    @PostMapping("/clickdate")
    public List<CalDateDetailDto> getClickDateList(@RequestBody ClickDateDto cddto, HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        cddto.setId(userId);

        List<CalDateDetailDto> list = scheduleService.getClickDateList(cddto);
        return list;
    }

    // 필터 조회
    @Auth
    @GetMapping("/filter")
    public List<FilterDto> getFilter(HttpServletRequest req) {
        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();

        List<FilterDto> list = scheduleService.getFilter(userId);
        System.out.println("플젝 필터~~~" + list);
        return list;
    }

    // 필터값 추가후 꺼내오기
    @Auth
    @PostMapping("/addcalitem")
    public String addCalFilterItem(@RequestBody String[] item, HttpServletRequest req) {

        UserVo user = (UserVo) req.getAttribute("user");
        String userId = user.getId();
        System.out.println("들어옴1");
        String calItem = "";
        for (int i = 0; i < item.length; i++) {
            calItem += item[i];
            if (i < item.length - 1) {
                calItem += ",";
            }
        }

        String calFilter = scheduleService.addCalFilterItem(calItem, userId);
        return calFilter;
    }

}