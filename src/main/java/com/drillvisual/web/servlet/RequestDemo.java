package com.drillvisual.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@WebServlet("/rt")
public class RequestDemo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        for (String hobby: hobbies) {
            System.out.println(hobby);
        }

        // 字符串方式获取单个参数
        String username = req.getParameter("username");
        System.out.println(username);
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
