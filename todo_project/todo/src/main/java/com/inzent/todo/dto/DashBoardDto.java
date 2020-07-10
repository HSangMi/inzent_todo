package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DashBoardDto {

    private String cTitle;
    private String startDate;
    private String endDate;
    private String regDate;
    private String state;
    private boolean usePublic;
    private String pTitle;
    private String prjTitle;
    private String prjId;
    private String cId;
    private int managerCount;
    private String managerName;
        private String img;

}