package com.hoolai.chatmonitor.open.aspect;

import com.google.common.base.Strings;
import com.hoolai.chatmonitor.open.dao.AdminLogDao;
import com.hoolai.chatmonitor.open.log.OperateLog;
import com.hoolai.chatmonitor.open.log.OperateLogHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    @Resource
    private AdminLogDao adminLogDao;

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.hoolai.chatmonitor.open.log.OperateLog)")
    public void cutLog() {
    }

    @Around("cutLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = proceedingJoinPoint.proceed();
        // 记录操作日志
        try {
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            OperateLog annotation = signature.getMethod().getAnnotation(OperateLog.class);
            String opName = annotation.value();
            String pKey = annotation.pKey();
            Object kValue = null;
            if (!Strings.isNullOrEmpty(pKey)) {
                Method pMethod = result.getClass().getMethod("get" + pKey.substring(0, 1).toUpperCase() + pKey.substring(1));
                kValue = pMethod.invoke(result);
            }
            adminLogDao.save(OperateLogHelper.createOperatorLog(result, opName, kValue.toString()));
        } catch (Exception e) {
            logger.error("记录日志错误！");
        }
        return result;
    }

}
