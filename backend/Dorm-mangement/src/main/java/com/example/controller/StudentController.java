package com.example.controller;

import com.example.pojo.dto.DormDto;
import com.example.pojo.dto.StudentDto;
import com.example.pojo.dto.StudentPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Student;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/student")
@Api(tags = "学生调试")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    @ApiOperation("分页查询学生信息")
    public Result<PageResult> page(StudentPageQueryDto studentPageQueryDto){
        log.info("分页查询学生信息:{}",studentPageQueryDto);
        PageResult pageResult= studentService.pageQuery(studentPageQueryDto);
        return Result.success(pageResult);
    }

    @PostMapping
    @ApiOperation("新增学生")
    public Result save(@RequestBody StudentDto studentDto){
        log.info("新增学生:{}",studentDto);
        studentService.save(studentDto);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询学生")
    public Result<Student> getById(@PathVariable long id){
        log.info("根据id查询学生:{}",id);
        Student student =studentService.getById(id);
        return Result.success(student);
    }

    @PutMapping
    @ApiOperation("修改学生")
    public Result update(@RequestBody StudentDto studentDto){
        log.info("修改学生:{}",studentDto);
        studentService.update(studentDto);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("学生批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("学生批量删除:{}",ids);
        studentService.deleteBatch(ids);
        return Result.success();
    }
}
