package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ScheduleDto {
    private String prjId;
    private String prjTitle;
    private String pId;
    private String pTitle;
    private String cId;
    private String cTitle;
    private String startDate;
    private String endDate;
    private String regDate;
    private String state;
    private boolean usePublic;
    private String managerName;
}