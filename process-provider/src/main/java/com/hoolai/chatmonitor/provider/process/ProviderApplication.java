package com.hoolai.chatmonitor.provider.process;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper")
@SpringBootApplication
public class ProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(ProviderApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ProviderApplication.class, args);
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot 使用profile为:【{}】", profile);
        }
    }
}
