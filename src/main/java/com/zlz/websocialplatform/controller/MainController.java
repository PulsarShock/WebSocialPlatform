package com.zlz.websocialplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {//后端不做页面跳转，通通交给前端，除了入口

    @RequestMapping("/")
    public String index(){
        return "redirect:/index.html" ;
    }

}
