package com.inzent.todo.service;

import java.util.List;

import com.inzent.todo.dto.ScheduleDto;
import com.inzent.todo.repository.ScheduleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleDao scheduledao;

    public List<ScheduleDto> getCalendatList(String userId){

        return scheduledao.getCalendatList(userId);
    }
}