package com.hoolai.chatmonitor.open.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoolai.chatmonitor.provider.process.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

//    @Resource
    @Reference
    private DemoService demoService;

    @GetMapping("ok")
    public String ok() {
        return "open-api: ok";
    }

    @GetMapping("hello")
    public String hello(String name) {
        String s = demoService.sayHello(name);
        return s;
    }
}
