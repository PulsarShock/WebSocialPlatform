package com.zlz.websocialplatform.controller;

import com.zlz.websocialplatform.entity.RestBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class MainController {//后端不做页面跳转，通通交给前端，除了入口

    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html" ;
    }

    @RequestMapping("/api/test")
    @ResponseBody
    public String test(){
        RestBean<String> rb=new RestBean<>(200,"success!");
        return rb.toString();
    }

}
