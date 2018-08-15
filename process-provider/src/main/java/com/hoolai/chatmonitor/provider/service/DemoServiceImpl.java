package com.hoolai.chatmonitor.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoolai.chatmonitor.DemoService;

import java.util.Random;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return name + " say hello! " + new Random().nextInt(100);
    }
}
