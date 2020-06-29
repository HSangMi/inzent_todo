package com.inzent.todo.dto;

import java.util.List;

import javax.crypto.Cipher;

import org.springframework.web.multipart.MultipartFile;

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
    private String dueDate;
    private String coverColor;
    private MultipartFile coverImg;
    private List<String> members;

    public boolean getUsePublic() {
        return usePublic;
    }

}