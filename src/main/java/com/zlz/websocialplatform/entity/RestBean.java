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
        /*
        这里因为泛型的原因，toJSONString解析不了，所以采用了idea模板来生成json
        本来模板能判断成员变量是否为字符串而选择是否添加双引号，这里又由于泛型的原因，模板无法判断，导致字符串直接拼接进json里，前端解析失败
        所以选择现在这种方式，只需要在此处做判断就行了，接下来开发过程中传入的T data应该都是类型明确的，所以不用担心出问题，可以不用这样写
        */
        if(data instanceof String){
            return "{" +
                    "\"code\":" + code +
                    ", \"reason\": \"" + reason + '\"' +
                    ", \"data\":\"" + data +'\"'+
                    "}";
        }
        return "{" +
                "\"code\":" + code +
                ", \"reason\": \"" + reason + '\"' +
                ", \"data\":" + data +
                "}";
    }
}
