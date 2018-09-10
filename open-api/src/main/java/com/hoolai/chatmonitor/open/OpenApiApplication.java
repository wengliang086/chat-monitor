package com.hoolai.chatmonitor.open;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.hoolai.chatmonitor.open.dao.mybatis.mapper")
@SpringBootApplication
@EnableSwagger2
public class OpenApiApplication {

    private static Logger logger = LoggerFactory.getLogger(OpenApiApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(OpenApiApplication.class, args);
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot 使用profile为:【{}】", profile);
        }
    }
}
