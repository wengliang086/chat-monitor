package com.hoolai.chatmonitor.common.returnvalue;

import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;

public class DefaultReturnCode implements ReturnCode {

    protected String group;

    /**
     * 错误码.
     */
    protected Integer code;

    /**
     * 提示信息.
     */
    protected String msg;

    public DefaultReturnCode() {
        this(HExceptionEnum.SUCCESS);
    }

    public DefaultReturnCode(String group, Integer code, String msg) {
        this.group = group;
        this.code = code;
        this.msg = msg;
    }

    public DefaultReturnCode(ReturnCode returnCode) {
        this(returnCode.getGroup(), returnCode.getCode(), returnCode.getMsg());
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "DefaultReturnCode{" +
                "group='" + group + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
