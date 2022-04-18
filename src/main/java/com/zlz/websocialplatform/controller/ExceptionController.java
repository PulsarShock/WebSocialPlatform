package com.zlz.websocialplatform.controller;

import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.exception.BaseProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(BaseProjectException.class)
    public String error(BaseProjectException e){
        RestBean<Void> restBean = new RestBean<>(e.getCode(),e.getReason(),null);
        log.info(e.getReason());
        return restBean.toString();
    }

}
