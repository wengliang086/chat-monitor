package com.hoolai.chatmonitor.provider.process;

import com.hoolai.chatmonitor.provider.process.dao.MsgSuspiciousDao;
import com.hoolai.chatmonitor.provider.process.dao.mybatis.vo.MsgSuspicious;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@MapperScan("com.hoolai.chatmonitor.provider.process.dao.mybatis.mapper")
@SpringBootApplication
public class ProcessProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(ProcessProviderApplication.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ProcessProviderApplication.class, args);
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            logger.warn("Spring Boot 使用profile为:【{}】", profile);
        }
        testDao(applicationContext);
    }

    private static void testDao(ApplicationContext applicationContext) {
        MsgSuspiciousDao dao = applicationContext.getBean(MsgSuspiciousDao.class);
        MsgSuspicious msgSuspicious = dao.get(1L);
        logger.info(msgSuspicious.toString());
    }
}
