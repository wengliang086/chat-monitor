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
        HExceptionEnum he = HExceptionEnum.SYSTEM_EXCEPTION;
        this.returnCode = new DefaultReturnCode(he.getGroup(), he.getCode(), message);
    }

    public HException(String group, Integer code, String msg) {
        this.returnCode = new DefaultReturnCode(group, code, msg);
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

    public static class HExceptionBuilder {

        private HException hException;

        private HExceptionBuilder(ReturnCode returnCode) {
            hException = new HException(returnCode);
        }

        private HExceptionBuilder(ReturnCode returnCode, String customDesc) {
            hException = new HException(returnCode.getGroup(), returnCode.getCode(), customDesc);
        }

        public HException build() {
            return hException;
        }

        public static HExceptionBuilder newBuilder(ReturnCode returnCode) {
            return new HExceptionBuilder(returnCode);
        }

        public static HExceptionBuilder newBuilder(ReturnCode returnCode, String customDesc) {
            return new HExceptionBuilder(returnCode, customDesc);
        }

    }
}
