package com.inzent.todo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CalFilterItemDto {
    private String[] calData;
    private String id;
}