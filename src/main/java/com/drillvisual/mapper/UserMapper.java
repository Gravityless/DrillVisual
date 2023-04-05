package com.drillvisual.mapper;

import com.drillvisual.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectById(int id);

    @Select("select * from \"user\" where name = #{name}")
    User selectByName(String name);
}
