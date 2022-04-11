package com.zlz.websocialplatform.entity;

import java.io.Serializable;

public class RestBean<T> {
    int code;
    String reason;
    T data;

    public RestBean(int code, String reason, T data) {
        this.code = code;
        this.reason = reason;
        this.data = data;
    }

    public RestBean(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public RestBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RestBean<T> setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestBean<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"reason\": \"" + reason + '\"' +
                ", \"data\":" + data +
                "}";
    }
}
