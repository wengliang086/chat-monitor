package com.hoolai.chatmonitor.provider.user;

import com.hoolai.chatmonitor.provider.user.config.MyApplicationRunner;
import com.hoolai.chatmonitor.provider.user.dao.UserLoginInfoDao;
import com.hoolai.chatmonitor.provider.user.dao.mybatis.vo.UserLoginInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.hoolai.chatmonitor.provider.user.dao.mybatis.mapper")
@SpringBootApplication
public class UserProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(UserProviderApplication.class, args);
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot 使用profile为:【{}】", profile);
        }
        UserLoginInfoDao dao = applicationContext.getBean(UserLoginInfoDao.class);
        UserLoginInfo userLoginInfo = dao.get(1002L);
        logger.info(userLoginInfo.toString());
    }
}
