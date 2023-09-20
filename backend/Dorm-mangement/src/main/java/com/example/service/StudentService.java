package com.example.service;

import com.example.pojo.dto.StudentDto;
import com.example.pojo.dto.StudentPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Student;
import com.example.result.PageResult;

import java.util.List;

public interface StudentService {

    /*分页查询学生信息*/
    PageResult pageQuery(StudentPageQueryDto studentPageQueryDto);

    /*新增学生*/
    void save(StudentDto studentDto);

    /*根据id查询学生*/
    Student getById(long id);

    /*修改学生*/
    void update(StudentDto studentDto);

    /*学生批量删除*/
    void deleteBatch(List<Long> ids);
}
