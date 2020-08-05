package com.inzent.todo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inzent.todo.dto.CalDateDetailDto;
import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
import com.inzent.todo.dto.ClickDateDto;
import com.inzent.todo.dto.FilterDto;
import com.inzent.todo.dto.GanttChartInfoDto;
import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.repository.ScheduleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduledao;

    // 캘린더 전체 조회 (처음 조회)
    public List<ScheduleDto> getCalendarList(String userId) {
        String existUser = scheduledao.selectExistUser(userId);
        String filterItem = scheduledao.selectCalFilterItem(userId);
        // System.out.println(filterItem);
        // PJ2020072109523258::orange,zzz::0
        List<ScheduleDto> list = new ArrayList<>();
        if (existUser.isEmpty() || filterItem.isEmpty() || filterItem.equals("::::0")) { // 필터값이 없다면
            list = scheduledao.getCalendarList(userId); // 전체 조회
        } else { // 필터 값이 있다면
            // 1. ::기준으로 배열에 담는다 (유형분류)
            String[] fItems = filterItem.split("::");

            // 2. 또 쪼개서 각 아이템배열에 넣는다
            String[] prjItem = fItems[0].split(","); // 프로젝트
            String[] memItem = fItems[1].split(","); // 담당자
            String uItem = fItems[2]; // 공개여부

            Map<String, Object> map = new HashMap<String, Object>();
            if (!prjItem[0].equals("")) {
                map.put("prjItem", prjItem);
            }
            if (!memItem[0].equals("")) {
                map.put("memItem", memItem);
            }
            map.put("uItem", uItem);
            map.put("userId", userId);

            list = scheduledao.getCalendarFilterList(map); // 필터적용 조회
        } // end else
        return list;
    }

    // 업무 등록에서 프로젝트 조회
    public List<Map<String, Object>> getProjects(String userId) {

        return scheduledao.getProjects(userId);
    }

    // 선택한 프로젝트의 업무대 조회
    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto chkprjdto) {

        return scheduledao.getSuperTasks(chkprjdto);
    }

    // 선택한 프로젝트 정보 가지고 있기....
    public Map<String, Object> getChkProjectInfo(ChkProjectDto chkprjdto) {

        return scheduledao.getChkProjectInfo(chkprjdto);
    }

    // 선택한 상위업무 정보 가지고 있기....
    public String getSubSortNo(String chkSuperTask) {

        return scheduledao.getSubSortNo(chkSuperTask);
    }

    // 해당 날짜의 업무 조회
    public List<CalDateDetailDto> getClickDateList(ClickDateDto cddto) {
        String existUser = scheduledao.selectExistUser(cddto.getId());
        String filterItem = scheduledao.selectCalFilterItem(cddto.getId());
        // System.out.println(filterItem);
        // // PJ2020072109523258::orange,zzz::0
        List<CalDateDetailDto> list = new ArrayList<>();
        if (existUser.isEmpty() || filterItem.isEmpty() || filterItem.equals("::::0")) { // 필터값이 없다면
            list = scheduledao.getClickDateList(cddto); // 전체 조회
        } else { // 필터 값이 있다면
            // // 1. ::기준으로 배열에 담는다 (유형분류)
            String[] fItems = filterItem.split("::");

            // // 2. 또 쪼개서 각 아이템배열에 넣는다
            String[] prjItem = fItems[0].split(","); // 프로젝트
            String[] memItem = fItems[1].split(","); // 담당자
            String uItem = fItems[2]; // 공개여부

            Map<String, Object> map = new HashMap<String, Object>();
            if (!prjItem[0].equals("")) {
                map.put("prjItem", prjItem);
            }
            if (!memItem[0].equals("")) {
                map.put("memItem", memItem);
            }
            map.put("uItem", uItem);
            map.put("clickDate", cddto.getClickDate());
            map.put("userId", cddto.getId());

            list = scheduledao.getClickDateFilterList(map);
            // System.out.println("상세필터적용" + list);
        } // end else
        return list;
    }

    // 해당 업무대의 업무 소 조회
    public List<CalDateDetailDto> getSubList(String superId) {
        return scheduledao.getSubList(superId);
    }

    // 프로젝트 필터 조회
    public List<FilterDto> getFilter(String userId) {
        return scheduledao.getFilter(userId);
    }

    public String getChkFilterItem(String userId) {
        return scheduledao.selectCalFilterItem(userId);
    }

    // 캘린더 필터 아이템 추가 및 조회
    public String addCalFilterItem(String calItem, String userId) {
        String existUser = scheduledao.selectExistUser(userId);
        int cnt = 0;
        String calFilter = "";
        if (existUser == null) { // 조회한 테이블에 user가 저장한 값이 없다면
            cnt = scheduledao.addCalFilterItem(calItem, userId); // 테이블에 값 추가
        } else { // 테이블에 user가 저장한 값이 있다면
            cnt = scheduledao.updateFilterItem(calItem, userId); // 해당 유저의 테이블 값 수정
        } // end else
        if (cnt == 1) { // 값이 잘 들어갔다면
            calFilter = scheduledao.selectCalFilterItem(userId); // 조회해라
        } else {
            // System.out.println("필터값 넣기 실패!");
        } // end else

        return calFilter;
    }

    // 필터 초기화
    public void resetCalFilter(String userId) {
        int cnt = scheduledao.resetCalFilter(userId);
        if (cnt == 1) {
            // System.out.println("삭제 성공");
        }
    }

    ////////////////////////////////////// 간트차트 //////////////////////////////

    public List<GanttChartInfoDto> getGanttChartSuperInfo(String userId) {
        List<GanttChartInfoDto> list = scheduledao.getGanttChartSuperInfo(userId);

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;
            long calDate = 0;
            long calDateDays = 0;

            for (GanttChartInfoDto dto : list) {
                if (dto.getStart().equals("") || dto.getEnd().equals("")) {
                    // 시작일이 없거나 마감일이 없으면
                    dto.setDuration(1);
                } else {
                    startDate = format.parse(dto.getStart());
                    endDate = format.parse(dto.getEnd());
                    calDate = endDate.getTime() - startDate.getTime();
                    calDateDays = calDate / (24 * 60 * 60 * 1000);
                    calDateDays = Math.abs(calDateDays);
                    dto.setDuration(calDateDays);
                } // end else
            } // end for
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<GanttChartInfoDto> getGanttChartSubInfo(String userId) {
        List<GanttChartInfoDto> list = scheduledao.getGanttChartSubInfo(userId);

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;
            long calDate = 0;
            long calDateDays = 0;

            for (GanttChartInfoDto dto : list) {
                if (dto.getStart().equals("") || dto.getEnd().equals("")) {
                    // 시작일이 없거나 마감일이 없으면
                    dto.setDuration(1);
                } else {
                    startDate = format.parse(dto.getStart());
                    endDate = format.parse(dto.getEnd());
                    calDate = endDate.getTime() - startDate.getTime();
                    calDateDays = calDate / (24 * 60 * 60 * 1000);
                    calDateDays = Math.abs(calDateDays);
                    dto.setDuration(calDateDays);
                } // end else
            } // end for
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

}