package com.drillvisual;

import com.alibaba.fastjson.JSON;
import com.drillvisual.pojo.User;

public class JsonDemo {
    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setName("jack");
        user.setPwd("1234");

        String json = JSON.toJSONString(user);
        System.out.println(json);

        User obj = JSON.parseObject("{\"id\":1,\"name\":\"jack\",\"pwd\":\"1234\"}", User.class);
        System.out.println(obj);
    }
}
