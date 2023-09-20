package com.example.controller;

import com.example.pojo.dto.FloorDto;
import com.example.pojo.entity.Floor;
import com.example.result.Result;
import com.example.service.FloorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/floor")
@Api(tags = "楼层调试")
public class FloorController {
    @Autowired
    private FloorService floorService;

    @GetMapping
    @ApiOperation("查询楼层信息")
    public Result<List<Floor>> selet(){
        log.info("查询楼层信息");
        List<Floor> list= floorService.select();
        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("新增楼层")
    public Result save(@RequestBody FloorDto floorDto){
        log.info("新增楼层:{}",floorDto);
        floorService.save(floorDto);
        return Result.success();
    }


    @GetMapping ("/{id}")
    @ApiOperation("根据id查询数据")
    public Result<Floor> getById(@PathVariable long id){
        log.info("根据id查询数据:{}",id);
        Floor floor=floorService.getById(id);
        return Result.success(floor);
    }

    @PutMapping
    @ApiOperation("修改楼层信息")
    public Result update(@RequestBody FloorDto floorDto){
        log.info("修改楼层信息:{}",floorDto);
        floorService.updateById(floorDto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除楼层")
    public Result deleteById(@PathVariable long id){
        log.info("根据id删除楼层:{}",id);
        floorService.deleteById(id);
        return Result.success();
    }
}
