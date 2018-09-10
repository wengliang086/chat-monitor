package com.hoolai.chatmonitor.open;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.hoolai.chatmonitor.open.dao.mybatis.mapper")
@SpringBootApplication
@EnableSwagger2
public class AdminApplication implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(AdminApplication.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                //是否允许证书 不再默认开启
                .allowCredentials(true)
                //设置允许的方法
                .allowedMethods("*")
                //跨域允许时间
                .maxAge(3600);
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AdminApplication.class, args);
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot 使用profile为:【{}】", profile);
        }
    }
}
