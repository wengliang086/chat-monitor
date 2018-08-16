package com.hoolai.chatmonitor.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

public class Log {

    private static final String thisClassName = Log.class.getName();
    private static final String msgSep = "\n";
    private static Logger log = getLogger("console");

    private static LogContext logContext;

    private static Logger getLogger(String name) {
        Logger logger = LoggerFactory.getLogger(name);
        return getProxy(logger);
    }

    private static Logger getLogger(Class<?> name) {
        Logger log = LoggerFactory.getLogger(name);
        return getProxy(log);
    }

    private static Logger getProxy(Logger log) {
        Object newProxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), log.getClass().getInterfaces(), new LoggerProxy(log));
        return (Logger) (newProxyInstance);
    }

    private static String getStackMsg(String msg) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String requestId = getRequestId();

        int srcFlag = 2;// 为了打印日志时，类信息不是该方法，而是真正的调用方法
        for (StackTraceElement stackTraceElement : stackTrace) {
            // 如果上一行堆栈代码是本类的堆栈，则该行代码则为源代码的最原始堆栈。
            if (srcFlag == 0) {
                return stackTraceElement == null ? "" : requestId + " " + stackTraceElement.toString() + msgSep + msg;
            }
            // 定位本类的堆栈
            if (thisClassName.equals(stackTraceElement.getClassName())) {
                srcFlag--;
            }
        }
        return null;
    }

    private static String getRequestId() {
        String requestId = "";
        if (logContext != null) {
            requestId = logContext.getRequestId();
        }
        if (requestId == null) {
            requestId = "";
        }
        requestId = requestId.trim();
        if (requestId.length() > 0) {
            requestId = "[" + requestId + "]";
        }
        return requestId;
    }

    public static void main(String[] args) {
        Log.setLogContext(() -> "TestLogContext");
        Log.info("test");
    }

    public static void setLogContext(LogContext logContext) {
        Log.logContext = logContext;
    }

    /**
     * 对外提供方法
     */

    public static void debug(String msg) {
        String message = getStackMsg(msg);
        log.debug(message);
    }

    public static void debug(String msg, Throwable t) {
        String message = getStackMsg(msg);
        log.debug(message, t);
    }

    public static void debug(String msg, Throwable t, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.debug(message, t);
    }

    public static void debug(String msg, Throwable t, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.debug(message, t);
    }

    public static void debug(String msg, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.debug(message);
    }

    public static void debug(String msg, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.debug(message);
    }

    public static void info(String msg) {
        String message = getStackMsg(msg);
        log.info(message);
    }

    public static void info(String msg, Throwable t) {
        String message = getStackMsg(msg);
        log.info(message, t);
    }

    public static void info(String msg, Throwable t, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.info(message, t);
    }

    public static void info(String msg, Throwable t, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.info(message, t);
    }

    public static void info(String msg, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.info(message);
    }

    public static void info(String msg, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.info(message);
    }

    public static void warn(String msg) {
        String message = getStackMsg(msg);
        log.warn(message);
    }

    public static void warn(String msg, Throwable t) {
        String message = getStackMsg(msg);
        log.warn(message, t);
    }

    public static void warn(String msg, Throwable t, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.warn(message, t);
    }

    public static void warn(String msg, Throwable t, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.warn(message, t);
    }

    public static void warn(String msg, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.warn(message);
    }

    public static void warn(String msg, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.warn(message);
    }

    public static void error(String msg) {
        String message = getStackMsg(msg);
        log.error(message);
    }

    public static void error(String msg, Throwable t) {
        String message = getStackMsg(msg);
        log.error(message, t);
    }

    public static void error(String msg, Throwable t, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.error(message, t);
    }

    public static void error(String msg, Throwable t, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.error(message, t);
    }

    public static void error(String msg, String name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.error(message);
    }

    public static void error(String msg, Class<?> name) {
        Logger log = getLogger(name);
        String message = getStackMsg(msg);
        log.error(message);
    }
}
