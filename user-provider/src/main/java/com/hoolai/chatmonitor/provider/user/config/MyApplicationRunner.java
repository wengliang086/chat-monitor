package com.hoolai.chatmonitor.provider.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    @Value("${config.variables.env}")
    private String env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String str = "开发测试";
        if ("online".equals(env)) {
            str = "生产";
        }
        logger.info("[ {} ] 环境启动成功！", str);

        logger.trace("======trace");
        logger.debug("======debug");
        logger.info("======info");
        logger.warn("======warn");
        logger.error("======error");
    }
}
