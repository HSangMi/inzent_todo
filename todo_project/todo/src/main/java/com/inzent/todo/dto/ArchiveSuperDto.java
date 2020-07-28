package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArchiveSuperDto {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String regDate;
    private String state;
    private String usePublic;
    private String sortNo;
    private String progressRate;
    private String memberNo;
    private String projectId;
    private String labels;
    private String projectTitle;
}