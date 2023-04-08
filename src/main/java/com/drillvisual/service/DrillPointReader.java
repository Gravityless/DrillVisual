package com.drillvisual.service;

import com.drillvisual.mapper.DrillPointMapper;
import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class DrillPointReader {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    public DrillPoint selectById(String id){
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取Mapper
        DrillPointMapper mapper = sqlSession.getMapper(DrillPointMapper.class);
        // 调用方法
        DrillPoint drillPoint = mapper.selectById(id);
        // 释放连接
        sqlSession.close();
        // 打印查询结果
        System.out.println(drillPoint);
        // 返回结果
        return drillPoint;
    }
}
