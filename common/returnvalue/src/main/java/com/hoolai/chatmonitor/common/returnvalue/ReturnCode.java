package com.hoolai.chatmonitor.common.returnvalue;

public interface ReturnCode {

    String getGroup();

    /**
     * 获取异常编码
     */
    Integer getCode();

    /**
     * 获取异常信息
     */
    String getMsg();
}
