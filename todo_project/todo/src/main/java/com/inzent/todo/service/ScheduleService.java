package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
import com.inzent.todo.dto.ClickDateDto;
import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.repository.ScheduleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduledao;

    // 캘린더 전체 조회
    public List<ScheduleDto> getCalendatList(String userId){
        return scheduledao.getCalendatList(userId);
    }

    // 선택한 프로젝트의 업무대 조회
    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto chkprjdto){

        return scheduledao.getSuperTasks(chkprjdto);
    }

    // 해당 날짜의 업무 조회
    public List<ScheduleDto> getClickDateList(ClickDateDto cddto){
        return scheduledao.getClickDateList(cddto);
    }
}