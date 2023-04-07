package com.drillvisual.web;


import com.drillvisual.pojo.User;
import com.drillvisual.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/TriTierArch")
public class TriTierArch extends HttpServlet {
    // 创建Service层对象
    private UserService service = new UserService();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 调用Service层完成查询
        List<User> users = service.selectAll();

        // 存入Request域中
        req.setAttribute("users", users);

        // 转发到View
        // req.getRequestDispatcher("/user.jsp").forward(req, res);
        System.out.println(users);

        res.getWriter().write(users.toString());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("doPost");
        this.doGet(req, res);
    }
}
