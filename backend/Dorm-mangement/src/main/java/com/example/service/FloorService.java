package com.example.service;

import com.example.pojo.dto.FloorDto;
import com.example.pojo.entity.Floor;

import java.util.List;

public interface FloorService {
    /*查询楼层信息*/
    List<Floor> select();

    /*新增楼层*/
    void save(FloorDto floorDto);

    /*根据id查询数据*/
    Floor getById(long id);

    /*修改楼层信息*/
    void updateById(FloorDto floorDto);

    /*根据id删除楼层*/
    void deleteById(long id);
}
