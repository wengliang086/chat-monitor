package com.hoolai.chatmonitor.open.aspect;

import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import com.hoolai.chatmonitor.open.auth.PermissionAnnotation;
import com.hoolai.chatmonitor.open.auth.PermissionType;
import com.hoolai.chatmonitor.open.model.UserLoginResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class AuthAspect {

    public static final String ACCESS_TOKEN = "accessToken";
    private final Logger logger = LoggerFactory.getLogger(AuthAspect.class);
    private static ConcurrentHashMap<String, UserLoginResponse> concurrentHashSet = new ConcurrentHashMap<>();

    public static UserLoginResponse get(HttpServletRequest request) {
        return concurrentHashSet.get(request.getHeader(ACCESS_TOKEN));
    }

    public static Integer getGroupId(HttpServletRequest request) {
        UserLoginResponse userLoginResponse = get(request);
        return userLoginResponse == null ? null : userLoginResponse.getGroupId();
    }

    @Pointcut("execution(public * com.hoolai.chatmonitor.open.controller.*.*(..))")
    public void loginCheck() {
    }

    @Before("loginCheck()")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        PermissionAnnotation classAnnotation = method.getDeclaringClass().getAnnotation(PermissionAnnotation.class);
        PermissionAnnotation annotation = method.getAnnotation(PermissionAnnotation.class);
        if (annotation != null) {
            ddd(joinPoint, annotation);
        } else if (classAnnotation != null) {
            ddd(joinPoint, classAnnotation);
        }
    }

    private void ddd(JoinPoint joinPoint, PermissionAnnotation annotation) {
        if (annotation.value() == PermissionType.LOGINED) {
//            logger.info("=======>>LOGINED");
            doLoginCheck(joinPoint);
        }
    }

    @Around("loginCheck()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Object proceed = proceedingJoinPoint.proceed();

        if (signature.getMethod().getName().equals("loginByAccount")) {
            UserLoginResponse user = (UserLoginResponse) proceed;
            concurrentHashSet.put(user.getAccessToken(), user);
//            logger.debug(JSON.toJSONString(concurrentHashSet));
        }
        return proceed;
    }

    private void doLoginCheck(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken == null) {
            accessToken = request.getParameter(ACCESS_TOKEN);
        }
        if (concurrentHashSet.get(accessToken) == null) {
            throw HException.HExceptionBuilder.newBuilder(HExceptionEnum.PLEASE_LOGIN_FIRST).build();
        }
    }

}
