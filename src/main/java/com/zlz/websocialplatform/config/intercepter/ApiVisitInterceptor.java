package com.zlz.websocialplatform.config.intercepter;

import com.zlz.websocialplatform.exception.BaseProjectException;
import com.zlz.websocialplatform.exception.ExceptionEnum;
import com.zlz.websocialplatform.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ApiVisitInterceptor implements HandlerInterceptor {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log.info("请求资源URI:"+request.getRequestURI());
        String user_email = request.getParameter("user_email");
        String user_token = request.getParameter("user_token");
        if(user_token == null){
            response.sendRedirect("/error/unauthorized");
            return false;
        }
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
            if(!user_token.equals(operations.get("Token:"+user_email))){
            response.sendRedirect("/error/invalidToken");
            return false;
        }
        return true;
    }


}
