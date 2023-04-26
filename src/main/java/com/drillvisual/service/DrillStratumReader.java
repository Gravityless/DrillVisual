package com.drillvisual.service;

import com.drillvisual.mapper.DrillStratumMapper;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class DrillStratumReader {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    public DrillStratum selectById(String id){
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取Mapper
        DrillStratumMapper mapper = sqlSession.getMapper(DrillStratumMapper.class);
        // 调用方法
        DrillStratum drillStratum = mapper.selectById(id);
        // 释放连接
        sqlSession.close();
        // 打印查询结果
        // System.out.println(drillStratum);
        // 返回结果
        return drillStratum;
    }

    public List<DrillStratum> selectByDrillId(String id) {
        // 获取SqlSession
        SqlSession sqlSession = factory.openSession();
        // 获取Mapper
        DrillStratumMapper mapper = sqlSession.getMapper(DrillStratumMapper.class);
        // 调用方法
        List<DrillStratum> drillStratums = mapper.selectByDrillId(id);
        // 释放连接
        sqlSession.close();
        // 打印查询结果
        // System.out.println(drillStratums);
        // 返回结果
        return drillStratums;
    }
}
