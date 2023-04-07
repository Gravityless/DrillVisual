package com.drillvisual.web;

import com.drillvisual.mapper.UserMapper;
import com.drillvisual.pojo.User;
import com.drillvisual.util.SqlSessionFactoryUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet("/rt")
public class RequestDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 设置Query编码（GET方式）Tomcat7以前需要设置此项
        // username = new String(username.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8)

        // 设置字符流Reader编码（POST方式）
        req.setCharacterEncoding("UTF-8");

        // 基础方法
        // String line = req.getQueryString();
        // System.out.println(line);

        // Map方式获取参数
        Map<String, String[]> map = req.getParameterMap();
        for (String key: map.keySet()) {
            System.out.print(key + ":");

            String[] values = map.get(key);
            for (String value: values) {
                System.out.print(value + " ");
            }

            System.out.println();
        }


        // 字符串数组方式获取多个参数
        String[] hobbies = req.getParameterValues("hobby");
        if (hobbies != null)
            for (String hobby: hobbies) {
                System.out.println(hobby);
            }

        // 字符串方式获取单个参数
        String username = req.getParameter("username");
        System.out.println(username);

        // 响应字符数据
        res.setContentType("text/html;charset=utf-8");
        PrintWriter writer = res.getWriter();
        // res.setHeader("content-type", "text/html");
        writer.write("response");
        writer.write("<h1>你好</h1>");

        // 响应字节数据
        // res.getOutputStream().write(binary-data);
        // IOUtils.copy(Input, Output);

        // 根据用户名查询实列
        username = req.getParameter("username");
        // 获取SqlSessionFactory对象，抽取到Util中执行
        // String resource = "mybatis-config.xml";
        // InputStream inputStream = Resources.getResourceAsStream(resource);
        // SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        // 获取SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 获取Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        // 调用方法
        User user = userMapper.selectByName(username);
        // 释放资源
        sqlSession.close();
        // 判断User
        if (user != null)
            writer.write("success!");
        else
            writer.write("failed!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        this.doGet(req, res);

        // 基础方法
        // BufferedReader br = req.getReader();
        // String line = br.readLine();
        // System.out.println(line);
    }
}
