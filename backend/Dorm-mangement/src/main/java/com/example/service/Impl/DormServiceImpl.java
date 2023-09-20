package com.example.service.Impl;

import com.example.constant.MessageConstant;
import com.example.context.BaseContext;
import com.example.exception.InsertNotAllowedException;
import com.example.mapper.DormMapper;
import com.example.mapper.FloorMapper;
import com.example.mapper.StudentMapper;
import com.example.pojo.dto.DormDto;
import com.example.pojo.dto.DormPageQueryDto;
import com.example.pojo.entity.Dorm;
import com.example.pojo.entity.Floor;
import com.example.pojo.entity.Student;
import com.example.result.PageResult;
import com.example.service.DormService;
import com.example.service.FloorService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DormServiceImpl implements DormService {

    @Autowired
    private DormMapper dormMapper;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private StudentMapper studentMapper;

    /*分页查询寝室*/
    @Override
    public PageResult pageQuery(DormPageQueryDto dormPageQueryDto) {
        PageHelper.startPage(dormPageQueryDto.getPage(),dormPageQueryDto.getPageSize());
        Page<Dorm> page= dormMapper.pageQuery(dormPageQueryDto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /*新增寝室*/
    @Override
    public void save(DormDto dormDto) {
        String dormNumber = dormDto.getDormNumber();
        String floorName = dormDto.getFloorName();

        /*寝室号不能重复*/
        Dorm dorm=dormMapper.contains(dormNumber);
        if(dorm!=null){
            throw new InsertNotAllowedException(MessageConstant.DORM_IS_EXIT);
        }

        /*楼层必须存在*/
        Floor floor=floorMapper.contains(floorName);
        if(floor==null){
            throw new InsertNotAllowedException(MessageConstant.FLOOR_NOT_EXIST);
        }

        /*寝室数量不超过最大寝室数量*/
        List<Dorm> list=dormMapper.check(floorName);
        long number = floor.getNumber();
        System.out.println(number);
        if(list.size()>=number){
            throw new InsertNotAllowedException(MessageConstant.FLOOR_IS_FULL);
        }

        /*添加寝室*/
        Dorm dorm1=new Dorm();
        BeanUtils.copyProperties(dormDto,dorm1);
        dormMapper.insert(dorm1);
    }

    /*根据id查询寝室*/
    @Override
    public Dorm getById(long id) {
        Dorm dorm=dormMapper.getById(id);
        return dorm;
    }

    /*修改寝室*/
    @Override
    public void update(DormDto dormDto) {
        long id = dormDto.getId();
        String dormNumber = dormDto.getDormNumber();
        String floorName = dormDto.getFloorName();
        Dorm dorm1 = dormMapper.getById(id);
        String floorName1 = dorm1.getFloorName();
        String dormNumber1 = dorm1.getDormNumber();
        if (dormNumber.equals(dormNumber1) && floorName.equals(floorName1)) {
            Dorm dorm2 = new Dorm();
            BeanUtils.copyProperties(dormDto, dorm2);
            dormMapper.updateById(dorm2);
        } else if (dormNumber.equals(dormNumber1) ) {

            /*楼层必须存在*/
            Floor floor = floorMapper.contains(floorName);
            if (floor == null) {
                throw new InsertNotAllowedException(MessageConstant.FLOOR_NOT_EXIST);
            }
            Dorm dorm2 = new Dorm();
            BeanUtils.copyProperties(dormDto, dorm2);
            dormMapper.updateById(dorm2);

        } else {

            /*寝室号不能重复*/
            Dorm dorm = dormMapper.contains(dormNumber);
            if (dorm != null) {
                throw new InsertNotAllowedException(MessageConstant.DORM_IS_EXIT);
            }

            /*楼层必须存在*/
            Floor floor = floorMapper.contains(floorName);
            if (floor == null) {
                throw new InsertNotAllowedException(MessageConstant.FLOOR_NOT_EXIST);
            }

            /*寝室数量不超过最大寝室数量*/
            List<Dorm> list = dormMapper.check(floorName);
            long number = floor.getNumber();
            System.out.println(number);
            if (list.size() > number) {
                throw new InsertNotAllowedException(MessageConstant.FLOOR_IS_FULL);
            }

            /*修改寝室*/
            Dorm dorm3 = new Dorm();
            BeanUtils.copyProperties(dormDto, dorm3);
            System.out.println(dorm3);
            dormMapper.updateById(dorm3);

            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();
            studentMapper.updateByDormNumber(dormNumber,dormNumber1,now,currentId);
        }
    }


    /*寝室批量删除*/
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        /*遍历所有的id，一个一个删除*/
        for (Long id : ids) {
            Dorm dorm = dormMapper.getById(id);
            String dormNumber = dorm.getDormNumber();
            System.out.println(dormNumber);
            dormMapper.deleteById(id);
            studentMapper.deleteByDormNumber(dormNumber);
        }
    }
}
