package com.zlz.websocialplatform.controller;

import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AuthApiController {

    @RequestMapping("/api/auth/login")
    @ResponseBody
    public String login(){
        RestBean<User> user=new RestBean<>(200,"呵呵了",new User("老陈","男",199));
        return user.toString();
    }

    @RequestMapping("/api/auth/logout")
    @ResponseBody
    public String logout(){
        return "hh";
    }

    @RequestMapping("/api/auth/signup")
    @ResponseBody
    public String signup(){
        return "kk";
    }

}
