package com.drillvisual.service;

import com.drillvisual.mapper.UserMapper;
import com.drillvisual.pojo.User;
import com.drillvisual.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class UserService {
    // 获取factory
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    public List<User> selectAll(){
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取Mapper
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        // 调用方法
        List<User> users = mapper.selectAll();
        // 释放连接
        sqlSession.close();
        // 返回结果
        return users;
    }
}
