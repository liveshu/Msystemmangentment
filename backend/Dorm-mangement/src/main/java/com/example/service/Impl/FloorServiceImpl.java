package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.exception.InsertNotAllowedException;
import com.example.mapper.DormMapper;
import com.example.mapper.FloorMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.dto.FloorDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Floor;
import com.example.service.FloorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private DormMapper dormMapper;

    @Autowired
    private StudentMapper studentMapper;

    /*查询楼层信息*/
    @Override
    public List<Floor> select() {
       return floorMapper.list();
    }

    /*新增楼层*/
    @Override
    public void save(FloorDto floorDto) {
        Floor floor=new Floor();
        BeanUtils.copyProperties(floorDto,floor);
        /*floor.setCreateTime(LocalDateTime.now());
        floor.setUpdateTime(LocalDateTime.now());

        floor.setCreateUser(BaseContext.getCurrentId());
        floor.setUpdateUser(BaseContext.getCurrentId());*/

        /*判断楼层是否存在*/
        String floorName = floor.getFloorName();
        Floor floor1 = floorMapper.contains(floorName);
        if(floor1!=null){
            throw new InsertNotAllowedException(MessageConstant.FLOOR_IS_EXIST);
        }

        /*添加楼层*/
        floorMapper.insert(floor);
    }

    /*根据id查询楼层信息*/
    @Override
    public Floor getById(long id) {
        Floor floor=floorMapper.getById(id);
        return floor;
    }

    /*修改楼层信息*/
    @Override
    @Transactional
    public void updateById(FloorDto floorDto) {
        Floor floor = new Floor();
        BeanUtils.copyProperties(floorDto, floor);
        long id = floor.getId();
        String newFloorName = floor.getFloorName();
        Floor floor1 = floorMapper.getById(id);
        String OldFloorName = floor1.getFloorName();
        System.out.println(OldFloorName);

        if (newFloorName.equals(OldFloorName)) {
            floorMapper.update(floor);
        } else {

            /*判断楼层是否已经存在*/
            String floorName = floor.getFloorName();
            Floor floor2 = floorMapper.contains(floorName);
            if (floor2 != null) {
                throw new InsertNotAllowedException(MessageConstant.FLOOR_IS_EXIST);
            }

            /*更新楼层*/
            floorMapper.update(floor);

            /*同时修改楼层信息*/
            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();
            dormMapper.updateByFloorName(OldFloorName, floorName, now, currentId);
        }
    }

    /*根据id删除楼层*/
    @Override
    @Transactional
    public void deleteById(long id) {
        Floor floor = floorMapper.getById(id);
        String floorName = floor.getFloorName();
        List<Dorm> list=dormMapper.getByFloorName(floorName);

        /*楼层删除与寝室删除*/
        floorMapper.deleteById(id);
        dormMapper.deleteByFloorName(floorName);

        /*学生删除*/
        for (Dorm dorm : list) {
            String dormNumber = dorm.getDormNumber();
            studentMapper.deleteByDormNumber(dormNumber);
        }
    }
}
