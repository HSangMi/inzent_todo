package com.inzent.todo.dto;

// import javax.crypto.Cipher;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectDto {

    private String title;
    private String description;
    private boolean usePublic;
    private String startDate;
    private String endDate;
    private String coverColor;
    private String coverImg;
    private String[] members;

    public boolean getUsePublic() {
        return usePublic;
    }

}