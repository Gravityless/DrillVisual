package com.drillvisual.web;


import com.drillvisual.pojo.DrillPoint;
import com.drillvisual.pojo.DrillStratum;
import com.drillvisual.service.*;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/FieldTestServlet")
public class FieldTestServlet extends HttpServlet {

    // 创建Service层对象
    private DrillPointReader drillPointReader = new DrillPointReader();
    private DrillStratumReader drillStratumReader = new DrillStratumReader();
    private StratumLineGenerator stratumLineGenerator = new StratumLineGenerator();
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 字符串数组方式获取多个参数
        // String[] drillIds = req.getParameterValues("drillId");
        // System.out.println(drillIds);

        // 设置字符流Reader编码（POST方式）
        req.setCharacterEncoding("UTF-8");
        // 响应字符数据
        res.setContentType("text/json;charset=utf-8");
        // 读取PayLoad
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while(line != null){
            builder.append(line);
            line = reader.readLine();
        }
        reader.close();
        String reqBody = builder.toString();
        // 将JSON数组转换为字符串数组
        String[] drillIds = JSON.parseObject(reqBody, String[].class);
        // 从Generator获取计算结果
        if (drillIds != null) {
            // 获取sectionPloter对象
            SectionPloter sectionPloter = stratumLineGenerator.generate(drillIds);
            // 计算横坐标相对距离
            sectionPloter.computeDistance();
            // 向Response中写入数据
            res.getWriter().write(JSON.toJSONString(sectionPloter));
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        System.out.println("doPost");
        this.doGet(req, res);
    }
}