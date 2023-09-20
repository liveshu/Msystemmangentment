package com.example.pojo.dto;

import lombok.Data;

@Data
public class StudentPageQueryDto {

    private int page;

    private int pageSize;

    private String dormNumber;

    private String student;

}
