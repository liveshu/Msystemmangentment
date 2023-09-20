package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.enumeration.OperationType;
import com.example.pojo.dto.StudentPageQueryDto;
import com.example.pojo.entity.Student;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StudentMapper {

    /*分页查询学生信息*/
    Page<Student> pageQuery(StudentPageQueryDto studentPageQueryDto);

    /*新增学生*/
    @AutoFill(value = OperationType.INSERT)
    void insert(Student student);

    /*根据id查询学生*/
    @Select("select *from student where id=#{id}")
    Student getById(long id);

    /*修改学生*/
    @AutoFill(value = OperationType.UPDATE)
    void updateById(Student student);

    /*学生批量删除*/
    @Delete("delete from student where id=#{id}")
    void deleteById(Long id);

    /*根据寝室号查询学生*/
    @Select("select *from student where dorm_number=#{dormNumber}")
    List<Student> number(String dormNumber);

    /*根据寝室号删除学生*/
    @Delete("delete from student where dorm_number=#{dormNumber}")
    void deleteByDormNumber(String dormNumber);

    /*更具寝室号修改学生*/
    @Update("update student set dorm_number=#{dormNumber},update_time=#{now},update_user=#{currentId}" +
            " where dorm_number=#{dormNumber1}")
    void updateByDormNumber(String dormNumber, String dormNumber1, LocalDateTime now, Long currentId);
}
