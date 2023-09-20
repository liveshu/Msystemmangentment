package com.example.service;

import com.example.pojo.dto.DormDto;
import com.example.pojo.dto.DormPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Floor;
import com.example.result.PageResult;

import java.util.List;

public interface DormService {

    /*分页查询寝室*/
    PageResult pageQuery(DormPageQueryDto dormPageQueryDto);

    /*新增寝室*/
    void save(DormDto dormDto);

    /*根据id查询寝室*/
    Dorm getById(long id);

    /*修改寝室*/
    void update(DormDto dormDto);

    /*寝室批量删除*/
    void deleteBatch(List<Long> ids);
}
