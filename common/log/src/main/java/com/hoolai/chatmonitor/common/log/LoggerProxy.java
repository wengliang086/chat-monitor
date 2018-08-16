package com.hoolai.chatmonitor.common.log;

import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class LoggerProxy implements InvocationHandler {

    private static Set<String> targetMethods = new HashSet<>();

    static {
        targetMethods.add("info");
        targetMethods.add("error");
        targetMethods.add("warn");
        targetMethods.add("debug");
        targetMethods.add("trace");
    }

    private Logger log;

    public LoggerProxy(Logger log) {
        super();
        this.log = log;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if (targetMethods.contains(method.getName())) {
//            Log.addLogTask(new MethodInvoker(log, method, args));
//            return null;
//        }
        return method.invoke(log, args);
    }
}
