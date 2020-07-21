package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateUserDto {
    private String id;
    private String key;
    private String value;
}