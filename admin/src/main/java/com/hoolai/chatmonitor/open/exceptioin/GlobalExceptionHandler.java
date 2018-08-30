package com.hoolai.chatmonitor.open.exceptioin;

import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import com.hoolai.chatmonitor.common.returnvalue.exception.HException;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 */
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(HException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ReturnValue notFount(HException e) {
        log.error("业务异常:", e.toString());
        return new ReturnValue<>(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ReturnValue exception(Exception e) {
        log.error("异常:", e);
        HException hException = HException.HExceptionBuilder.newBuilder(HExceptionEnum.SYSTEM_EXCEPTION, e.getMessage()).build();
        return new ReturnValue<>(hException);
    }

}
