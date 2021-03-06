package com.zlz.websocialplatform.entity.exception;

public enum ExceptionEnum {

    UNAUTHORIZED(401,"未授权！"),
    INVALID_TOKEN(402,"口令错误或过期！"),
    LOGGING_FAILED(403,"登录失败！"),
    NOT_FOUND(404,"请求的资源不存在或已被删除！"),
    LOGOUT_FAILED(405,"登出失败！"),
    TIME_OUT(406,"超时！"),
    INTERNAL_ERROR(500,"服务器内部错误！"),

    USED_EMAIL_ADDRESS(100401,"用户邮箱已注册！"),
    WRONG_VERIFICATION_CODE(100402,"验证码错误！"),
    VERIFICATION_CODE_STILL_EXIST(100403,"验证码未过期！"),
    VERIFICATION_CODE_SEND_FAILED(100404,"验证码发送失败！"),

    COMMENT_CLOSED(101401,"该动态不允许评论！")
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
