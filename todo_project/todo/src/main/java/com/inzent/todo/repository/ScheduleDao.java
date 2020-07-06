package com.inzent.todo.repository;

import java.util.List;

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

        return sqlsession.selectList("calendar.getCalendarList", userId);
    }

    public List<ChkSuperTasksDto> getSuperTasks(ChkProjectDto cpdto) {

        return sqlsession.selectList("calendar.getSuperTasks", cpdto);
    }
}