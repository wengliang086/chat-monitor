package com.hoolai.chatmonitor.common.log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {

    private Method method;
    private Object obj;
    private Object[] args;

    public MethodInvoker(Object obj, Method method, Object[] args) {
        this.method = method;
        this.obj = obj;
        this.args = args;
    }

    public void invoke() {
        try {
            method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
