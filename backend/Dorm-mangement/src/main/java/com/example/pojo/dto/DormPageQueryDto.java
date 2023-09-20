package com.example.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DormPageQueryDto implements Serializable {

    private int page;

    private int pageSize;

    private String dormNumber;

    private String floorName;


}
