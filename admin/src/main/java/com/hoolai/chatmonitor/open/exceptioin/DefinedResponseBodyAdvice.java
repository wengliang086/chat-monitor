package com.hoolai.chatmonitor.open.exceptioin;

import com.alibaba.fastjson.JSON;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class DefinedResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body == null || body instanceof ReturnValue) {
            return body;
        }

        if (selectedConverterType.equals(StringHttpMessageConverter.class)) {
            System.out.println(body + " " + returnType);
        }

        if (body instanceof String) {
            return JSON.toJSONString(new ReturnValue<>(body));
        }

        return new ReturnValue<>(body);
    }
}
