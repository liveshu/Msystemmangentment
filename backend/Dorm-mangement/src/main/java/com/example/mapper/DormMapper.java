package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.enumeration.OperationType;
import com.example.pojo.dto.DormPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Floor;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DormMapper {

    Page<Dorm> pageQuery(DormPageQueryDto dormPageQueryDto);

    @AutoFill(OperationType.INSERT)
    void insert(Dorm dorm);

    @Select("select *from dormitory where id=#{id}")
    Dorm getById(long id);

    @AutoFill(value = OperationType.UPDATE)
    void updateById(Dorm dorm);

    /*寝室批量删除*/
    @Delete("delete from dormitory where id=#{id}")
    void deleteById(Long id);

    /*根据寝室号查询寝室*/
    @Select("select *from dormitory where dorm_number=#{dormNumber}")
    Dorm contains(String dormNumber);

    /*根据楼层查询寝室*/
    @Select("select *from dormitory where floor_name=#{floorName}")
    List<Dorm> check(String floorName);

    /*同时修改寝室中的楼层信息*/
    @Update("update dormitory set floor_name=#{floorName},update_time=#{now},update_user=#{currentId} where floor_name=#{oldFloorName}")
    void updateByFloorName(String oldFloorName, String floorName, LocalDateTime now,Long currentId);

    /*通过楼层名字获取寝室信息*/
    @Select("select *from dormitory where floor_name=#{floorName}")
    List<Dorm> getByFloorName(String floorName);

    /*通过楼层名字删除寝室*/
    @Delete("delete from dormitory where floor_name=#{floorName}")
    void deleteByFloorName(String floorName);
}
