package com.hoolai.chatmonitor.open.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.hoolai.chatmonitor.common.returnvalue.ReturnValue;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(0)
@Component
@WebFilter(filterName = "", urlPatterns = "/*")
public class GlobalFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MyResponseWrapper responseWrapper = new MyResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest, responseWrapper);
        String responseContent = new String(responseWrapper.getDataStream());
        Object parse = null;
        try {
            parse = JSON.parse(responseContent);
        } catch (JSONException e) {
            parse = responseContent;
        }
        String jsonString = JSON.toJSONString(new ReturnValue<>(parse));
        servletResponse.setContentLength(-1);
        servletResponse.getOutputStream().write(jsonString.getBytes());
    }

    @Override
    public void destroy() {
    }
}
