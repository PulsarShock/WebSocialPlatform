package com.zlz.websocialplatform.exception;

public class BaseProjectException extends RuntimeException{

    protected int code;
    protected String reason;

    public BaseProjectException(ExceptionEnum exceptionEnum){
        this.code=exceptionEnum.getCode();
        this.reason=exceptionEnum.getReason();
    }

    public String getReason(){
        return this.reason;
    }

    public int getCode() {
        return code;
    }
}
