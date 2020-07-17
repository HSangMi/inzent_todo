package com.inzent.todo.service;

import java.util.ArrayList;
import java.util.List;

import com.inzent.todo.dto.CalDateDetailDto;
import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
import com.inzent.todo.dto.ClickDateDto;
import com.inzent.todo.dto.FilterDto;
import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.repository.ScheduleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduledao;

    // 캘린더 전체 조회
    public List<ScheduleDto> getCalendarList(String userId) {
        String existUser = scheduledao.selectExistUser(userId);
        String filterItem = scheduledao.selectCalFilterItem(userId);
        System.out.println("필터가 잘 왓냐능,,,," + filterItem);
        List<ScheduleDto> list = new ArrayList<>();
        if (existUser == null || filterItem == "") { // 유저아이디가 없거나 filterItem이 ""라면
            list = scheduledao.getCalendarList(userId); // 전체 조회
        } else {
            list = scheduledao.getCalendarFilterList(userId); // 필터적용 조회
        }
        return list;
    }

    // 선택한 프로젝트의 업무대 조회
    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto chkprjdto) {

        return scheduledao.getSuperTasks(chkprjdto);
    }

    // 해당 날짜의 업무 조회
    public List<CalDateDetailDto> getClickDateList(ClickDateDto cddto) {
        return scheduledao.getClickDateList(cddto);
    }

    // 프로젝트 필터 조회
    public List<FilterDto> getFilter(String userId) {
        return scheduledao.getFilter(userId);
    }

    // 캘린더 필터 아이템 추가 및 조회
    public String addCalFilterItem(String calItem, String userId) {
        String existUser = scheduledao.selectExistUser(userId);
        String calFilter = "";
        int cnt = 0;
        if (existUser == null) { // 조회한 테이블에 user가 저장한 값이 없다면
            cnt = scheduledao.addCalFilterItem(calItem, userId); // 테이블에 값 추가
        } else { // 테이블에 user가 저장한 값이 있다면
            cnt = scheduledao.updateFilterItem(calItem, userId); // 해당 유저의 테이블 값 수정
        } // end else
        if (cnt == 1) { // 값이 잘 들어갔다면
            calFilter = scheduledao.selectCalFilterItem(userId); // 조회해라
        } else {
            System.out.println("필터값 넣기 실패~~!");
        } // end else

        return calFilter;
    }

}