package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.enumeration.OperationType;
import com.example.pojo.entity.Floor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FloorMapper {

    @Select("select *from floor")
    List<Floor> list();

    @AutoFill(value = OperationType.INSERT)
    void insert(Floor floor);

    @Select("select *from floor where id=#{id}")
    Floor getById(long id);

    @AutoFill(value = OperationType.UPDATE)
    void update(Floor floor);

    @Delete("delete from floor where id=#{id}")
    void deleteById(long id);

    @Select("select *from floor where floor_name=#{floorName}")
    Floor contains(String floorName);

}
