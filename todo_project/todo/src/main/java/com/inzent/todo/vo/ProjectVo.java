package com.inzent.todo.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectVo {
    private String id;
    private String title;
    private String state;
    private boolean usePublic;
    private String description;
    private String startDate;
    private String dueDate;
    private Date regDate;
    private String imgNo;
    private int manager;

    public boolean getUsePublic() {
        return usePublic;
    }

}