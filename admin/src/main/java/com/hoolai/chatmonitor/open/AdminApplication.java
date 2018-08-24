package com.hoolai.chatmonitor.open;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@MapperScan("com.hoolai.chatmonitor.open.dao.mybatis.mapper")
@SpringBootApplication
public class AdminApplication extends WebMvcConfigurationSupport {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
