package com.drillvisual.mapper;

import com.drillvisual.pojo.User;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    User selectById(int id);
}
