package com.hoolai.chatmonitor.open.controller;

import com.alibaba.fastjson.JSONObject;
import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PermissionAnnotation(PermissionType.LOGINED)
@RestController
@RequestMapping("test")
public class TestController {

    @PermissionAnnotation(PermissionType.PUBLIC)
    @GetMapping("ok")
    public String ok() {
        return "ok";
    }

    @GetMapping("ok1")
    public String ok1() {
        return "ok1";
    }

    @RequestMapping("login")
    public String login() {
        JSONObject o = new JSONObject();
        o.put("code", 200);
        o.put("msg", "请求成功");

        JSONObject u = new JSONObject();
        u.put("id", 1);
        u.put("name", "whq");
        u.put("username", "admin");
        u.put("avatar", "https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png");

        o.put("user", u);
        return o.toJSONString();
    }
}
