package com.hoolai.chatmonitor.common.returnvalue.exception.enums;

import com.hoolai.chatmonitor.common.returnvalue.ReturnCode;

public enum HExceptionEnum implements ReturnCode {


    SUCCESS(1, "成功"),
    /**
     * 其他
     */
    UNKNOW_ERROR(-1, "未知错误"),
    UID_NOT_FIND(2, "UID 不存在"),
    SENSITIVE_WORD_FIND(3, "存在敏感词汇"),
    TIME_ERROR(4, "时间错误"),
    TIME_TYPE_ERROR(5, "时间类型错误"),
    APP_ID_ERROR(6, "appid错误"),

    ACCOUNT_ALREDAY_EXIST("账户已经存在"),
    ACCOUNT_IS_INVALID("登陆账户必须只有字母、数字和下划线且必须以字母开头，不能以下划线结尾"),
    EMAIL_ALREDAY_EXIST("邮箱已经存在"),
    EMAIL_IS_INVALID("邮箱格式错误"),
    PHONE_ALREDAY_EXIST("手机号码已经存在"),
    PHONE_NOT_EXIST("手机号码不存在"),
    PHONE_IS_INVALID("手机号码格式错误"),
    PASSWORD_CAN_NOT_BE_EMPTY("密码不能为空"),
    UDID_CAN_NOT_BE_EMPTY("UDID不能为空"),
    OLD_PASSWORD_ERROR("原密码输入错误"),
    PASSWORD_IS_INVALID("密码必须是数字或者字母或者半角符号的组合,不能有空格,长度6-20位"),
    LOGIN_INFO_NOT_FOUND("登陆信息未找到,uid[#{uid}]"),
    ACCOUNT_INFO_NOT_FOUND("登陆信息未找到,account[#{account}]"),
    PHONE_INFO_NOT_FOUND("登陆信息未找到,phone[#{phone}]"),
    PARAMETERS_CAN_NOT_BE_REPEATED("参数不可以重复"),
    LOGIN_FAILED("登陆名或者密码错误"),
    OPERATION_TOO_FREQUENT("操作频繁"),
    PLEASE_TRY_LATER("请稍后再试！"),
    ACCOUNT_NOT_EXIST("该账号不存在！"),
    PHPNE_NUMBER_ERROR("输入手机号码有误！"),
    VALIDATE_CODE_NOT_EXIST("验证码信息不存在！"),
    CODE_ERROR("请输入正确的验证码！"),
    NAME_ERROR("请输入姓名"),
    IDENTITY_CARD_ERROR("请输入正确的身份证号码！"),
    VALIDATE_CODE_OVERDUE("验证码已过期"),

    SYSTEM_EXCEPTION(500, "服务器异常");

    private static final String group = "hException";
    private Integer code;
    private String message;

    HExceptionEnum(String msg) {
        this(-1, msg);
    }

    HExceptionEnum(int code, String msg) {
        this.code = code;
        this.message = msg;
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
        return message;
    }
}
