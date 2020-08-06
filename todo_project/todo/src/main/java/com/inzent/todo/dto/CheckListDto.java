package com.inzent.todo.dto;

import com.inzent.todo.vo.CheckListVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CheckListDto {
    private int listNo;
    private String title;
    private String taskId;
    private int progressRate;
    private int memberNo;
    private String checkListItems;
}