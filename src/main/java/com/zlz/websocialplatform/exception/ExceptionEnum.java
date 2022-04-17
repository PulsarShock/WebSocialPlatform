package com.zlz.websocialplatform.exception;

public enum ExceptionEnum {

    UNAUTHORIZED(401,"未授权！"),
    AUTHORIZATION_OUT_OF_TIME(402,"授权已过期！"),
    LOGGING_FAILED(403,"登录失败！"),
    NOT_FOUND(404,"未找到资源！"),
    LOGOUT_FAILED(405,"登出失败！"),

    USED_EMAIL_ADDRESS(100401,"用户邮箱已注册！"),
    WRONG_VERIFICATION_CODE(100402,"验证码错误！"),
    VERIFICATION_CODE_STILL_EXIST(100403,"验证码未过期！"),
    VERIFICATION_CODE_SEND_FAILED(100404,"验证码发送失败！")
    ;

    private final int code;
    private final String reason;

    ExceptionEnum(int code, String reason) {
        this.code=code;
        this.reason=reason;
    }

    public int getCode(){
        return this.code;
    }

    public String getReason(){
        return this.reason;
    }

}
