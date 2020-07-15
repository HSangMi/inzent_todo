package com.inzent.todo.repository;

import java.util.List;

import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
import com.inzent.todo.dto.ClickDateDto;
import com.inzent.todo.dto.FilterDto;
import com.inzent.todo.dto.ScheduleDto;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao {

    @Autowired
    private SqlSession sqlsession;

    public List<ScheduleDto> getCalendatList(String userId) {
        List<ScheduleDto> prjInfo = sqlsession.selectList("calendar.getCalendarInfo", userId);
        return prjInfo;
    }

    // public List<Map<String,String>> getCalendarManagers(String userId){
    // List<Map<String,String>> list = sqlsession.selectList("calendar.getManagers",
    // userId);
    // System.out.println(list);
    // return list;
    // }

    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto chkprjdto) {

        return sqlsession.selectList("calendar.getSuperTasks", chkprjdto);
    }

    public List<ScheduleDto> getClickDateList(ClickDateDto cddto) {
        return sqlsession.selectList("calendar.getClickDateList", cddto);
    }

    public List<FilterDto> getFilter(String userId) {
        return sqlsession.selectList("calendar.getFilter", userId);
    }
}