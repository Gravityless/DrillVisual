package com.drillvisual.web;

import com.alibaba.fastjson.JSON;
import com.drillvisual.pojo.Section;
import com.drillvisual.service.PolygonGenerator;
import com.drillvisual.service.StratumLineGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/ChartPlot")
public class ChartPlot extends HttpServlet {
    // 创建Service层对象
    private StratumLineGenerator stratumLineGenerator = new StratumLineGenerator();
    private PolygonGenerator polygonGenerator = new PolygonGenerator();
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
        System.out.println("DEBUG >>> Request array: " + reqBody);
        // 将JSON数组转换为字符串数组
        // String[] drillPoints = JSON.parseObject(reqBody, String[].class);
        Double[][] drillPoints = JSON.parseObject(reqBody, Double[][].class);
        // 从Generator获取计算结果
        if (drillPoints != null) {
            // 生成地层线
            Section section = stratumLineGenerator.generate(drillPoints);
            // 生成地层面
            polygonGenerator.generate(section);
            // 向Response中写入数据
            res.getWriter().write(JSON.toJSONString(section));
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // System.out.println("doPost");
        this.doGet(req, res);
    }
}
