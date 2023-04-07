package com.drillvisual.web;

import com.alibaba.fastjson.JSON;
import com.drillvisual.pojo.User;
import com.drillvisual.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/JsonServlet")
public class JsonServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // 调用Service
        List<User> users = userService.selectAll();
        // 序列化对象
        String json = JSON.toJSONString(users);
        // 响应数据
        res.setContentType("text/json;charset=utf-8");
        res.getWriter().write(json);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        this.doGet(req, res);
    }
}
