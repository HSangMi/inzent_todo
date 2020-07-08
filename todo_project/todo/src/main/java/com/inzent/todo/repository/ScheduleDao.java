package com.inzent.todo.repository;

import java.util.List;
import java.util.Map;

import com.inzent.todo.dto.ChkProjectDto;
import com.inzent.todo.dto.ChkSuperTasksDto;
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
        System.out.println(prjInfo);
        return prjInfo;
    }

    // public List<Map<String,String>> getCalendarManagers(String userId){
    //     List<Map<String,String>> list = sqlsession.selectList("calendar.getManagers", userId);
    //     System.out.println(list);
    //     return list;
    // }

    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto chkprjdto) {

        return sqlsession.selectList("calendar.getSuperTasks", chkprjdto);
    }
}