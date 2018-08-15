package com.hoolai.chatmonitor.open;

import com.alibaba.dubbo.container.Main;

import java.io.IOException;

public class ProcessProvider {

    public static void main(String[] args) throws IOException {
        Main.main(args);
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
//        context.start();
//        System.in.read(); // 按任意键退出
    }
}
