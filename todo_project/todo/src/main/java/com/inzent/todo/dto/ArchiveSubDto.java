package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArchiveSubDto {
    private String id;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private String regDate;
    private String state;
    private String usePublic;
    private String sortNo;
    private String memberNo;
    private String taskSuperId;
    private String labels;
    private String superTitle;
    private String projectTitle;
}