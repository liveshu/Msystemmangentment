package com.example.mapper;

import com.example.annotation.AutoFill;
import com.example.enumeration.OperationType;
import com.example.pojo.entity.Login;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("select *from admin where username = #{username}")
    Login getByUsername(String username);

    /*根据用户名查找*/
    @Select("select * from admin where username=#{username} ")
    Login contains(String username);

    /*增加用户*/
    @AutoFill(value = OperationType.INSERT)
    @Insert("insert into admin(name, username, password, create_time, update_time, create_user, update_user) " +
            "VALUES (#{name},#{username},#{password},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void create(Login login1);
}
