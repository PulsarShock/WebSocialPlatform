package com.zlz.websocialplatform.config.intercepter;

import com.zlz.websocialplatform.exception.BaseProjectException;
import com.zlz.websocialplatform.exception.ExceptionEnum;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiVisitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user_token") == null){
            throw new BaseProjectException(ExceptionEnum.AUTHORIZATION_OUT_OF_TIME);
        }
        return true;
    }


}
