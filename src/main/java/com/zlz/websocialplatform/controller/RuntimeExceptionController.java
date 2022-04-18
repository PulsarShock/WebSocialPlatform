package com.zlz.websocialplatform.controller;

import com.zlz.websocialplatform.entity.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(10)
@RestControllerAdvice
@Slf4j
public class RuntimeExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e){
        RestBean<Void> rb = new RestBean<>(500,"服务器内部错误");
        log.info("内部错误！");
        e.printStackTrace();
        return rb.toString();
    }

}
