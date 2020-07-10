package com.inzent.todo.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaskDto {
    private String taskId;
    private String title;
    private String description;
    private boolean usePublic;
    private Date regDate;
    private String startDate;
    private String endDate;
    private char state;
    private int sortNo;
    private int progressRate;
    private int memberNo;
    private String projectId;
    private String taskSuperId;
    private String labels;
    private MultipartFile[] attachFiles;
    private int fileCnt;
    private int commentCnt;
    private String manager;

    public boolean getUsePublic() {
        return usePublic;
    }
}