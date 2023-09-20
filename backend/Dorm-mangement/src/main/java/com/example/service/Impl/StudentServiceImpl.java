package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.exception.InsertNotAllowedException;
import com.example.mapper.DormMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.dto.StudentDto;
import com.example.pojo.dto.StudentPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Student;
import com.example.result.PageResult;
import com.example.service.StudentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private DormMapper dormMapper;

    /*分页查询学生信息*/
    @Override
    public PageResult pageQuery(StudentPageQueryDto studentPageQueryDto) {
        PageHelper.startPage(studentPageQueryDto.getPage(),studentPageQueryDto.getPageSize());
        Page<Student> page= studentMapper.pageQuery(studentPageQueryDto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*新增学生*/
    @Override
    public void save(StudentDto studentDto) {
        String dormNumber = studentDto.getDormNumber();
        /*判断该寝室是否存在*/
        Dorm dorm=dormMapper.contains(dormNumber);
        System.out.println(dorm);
        if(dorm==null){
            throw new InsertNotAllowedException(MessageConstant.DORM_NOT_EXIST);
        }

        /*判断该寝室人数是否已满*/
        List<Student> list=studentMapper.number(dormNumber);
        if(list.size()>3){
            throw new InsertNotAllowedException(MessageConstant.STUDENT_ISFULL);
        }
        /*添加学生*/
        Student student=new Student();
        BeanUtils.copyProperties(studentDto,student);
        studentMapper.insert(student);
    }

    /*根据id查询学生*/
    @Override
    public Student getById(long id) {
        Student student=studentMapper.getById(id);
        return student;
    }

    /*修改学生*/
    @Override
    public void update(StudentDto studentDto) {
        String dormNumber = studentDto.getDormNumber();
        /*判断该寝室是否存在*/
        Dorm dorm=dormMapper.contains(dormNumber);
        if(dorm==null){
            throw new InsertNotAllowedException(MessageConstant.DORM_NOT_EXIST);
        }

        /*判断该寝室人数是否已满*/
        List<Student> list=studentMapper.number(dormNumber);
        if(list.size()>3){
            throw new InsertNotAllowedException(MessageConstant.STUDENT_ISFULL);
        }
        /*修改学生信息*/
        Student student=new Student();
        BeanUtils.copyProperties(studentDto,student);
        System.out.println(student);
        studentMapper.updateById(student);
    }

    /*学生批量删除*/
    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            studentMapper.deleteById(id);
        }
    }
}
