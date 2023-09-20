package com.example.controller;

import com.example.pojo.dto.DormDto;
import com.example.pojo.dto.DormPageQueryDto;
import com.example.pojo.dto.FloorDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Floor;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.DormService;
import com.example.service.FloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/dorm")
@Api(tags = "寝室调试")
public class DormController {
    @Autowired
    private DormService dormService;

    @GetMapping()
    @ApiOperation("分页查询寝室")
    public Result<PageResult> page(DormPageQueryDto dormPageQueryDto){
        log.info("分页查询寝室 :{}",dormPageQueryDto);
        PageResult pageResult= dormService.pageQuery(dormPageQueryDto);
        return Result.success(pageResult);
    }


    @PostMapping
    @ApiOperation("新增寝室")
    public Result save(@RequestBody DormDto dormDto){
        log.info("新增寝室:{}",dormDto);
        dormService.save(dormDto);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询寝室")
    public Result<Dorm> getById(@PathVariable long id){
        log.info("根据id查询寝室:{}",id);
        Dorm dorm=dormService.getById(id);
        return Result.success(dorm);
    }

    @PutMapping
    @ApiOperation("修改寝室")
    public Result update(@RequestBody DormDto dormDto){
        log.info("修改寝室:{}",dormDto);
        dormService.update(dormDto);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("寝室批量删除")
    public Result delete(@RequestParam List<Long> ids){
        log.info("寝室批量删除:{}",ids);
        dormService.deleteBatch(ids);
        return Result.success();
    }
}
