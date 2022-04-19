package com.zlz.websocialplatform.config.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PageInterceptor implements HandlerInterceptor {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log.info("请求资源URI:"+request.getRequestURI());
        String sessionId=request.getSession().getId();
        String user_email = request.getParameter("user_email");
        String user_token = request.getParameter("user_token");
        //log.info("页面请求，来自[邮箱:{}，sessionID:{}，Token:{}]",user_email,sessionId,user_token);
        if(user_token == null){
            response.sendRedirect("/form-login.html");
            return false;
        }
        ValueOperations<String,String> operations = stringRedisTemplate.opsForValue();
        if(!user_token.equals(operations.get(user_email+":Token:"+sessionId))){
            response.sendRedirect("/form-login.html");
            return false;
        }
        return true;
    }

}
