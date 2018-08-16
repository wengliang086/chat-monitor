package com.hoolai.chatmonitor.common.returnvalue.exception;

import com.hoolai.chatmonitor.common.returnvalue.DefaultReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.ReturnCode;
import com.hoolai.chatmonitor.common.returnvalue.exception.enums.HExceptionEnum;

public class HException extends RuntimeException implements ReturnCode {

    private ReturnCode returnCode;

    public HException() {
        this(new DefaultReturnCode());
    }

    public HException(ReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public HException(String message, Throwable cause) {
        super(message, cause);
        this.returnCode = new DefaultReturnCode(HExceptionEnum.SYSTEM_EXCEPTION);
    }

    public HException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    @Override
    public String getGroup() {
        return returnCode.getGroup();
    }

    @Override
    public Integer getCode() {
        return returnCode.getCode();
    }

    @Override
    public String getMsg() {
        return returnCode.getMsg();
    }

    @Override
    public String toString() {
        return "HException{" +
                "returnCode=" + returnCode +
                '}';
    }
}
