package com.zlz.websocialplatform.controller.api;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;
import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.service.UserAuthService;
import com.zlz.websocialplatform.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    UserAuthService userAuthService;
    @Resource
    VerifyCodeService verifyCodeService;

    @PostMapping("/login")
    public String login(@RequestBody String userInfo){
        log.info("登录用户信息：{}",userInfo);
        Account account = JSON.parseObject(userInfo).toJavaObject(Account.class);
        RestBean<String> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        account.setUserEmail("Token:"+account.getUserEmail());
        String token=userAuthService.loginProcess(account);
        return resp.setData(token).toString();
    }

    @GetMapping("/logout")
    public String logout(@RequestHeader("user_email") String logoutUserEmail,@RequestHeader("user_token") String token){
        log.info("用户 {} 申请登出",logoutUserEmail);
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        userAuthService.logoutProcess("Token:"+logoutUserEmail,token);
        log.info("用户 {} 登出成功！",logoutUserEmail);
        return resp.toString();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody String signupInfo){
        AccountForSignup account=JSON.parseObject(signupInfo).toJavaObject(AccountForSignup.class);
        log.info("注册用户信息：{}",account.toString());
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        account.setUserEmail("Code:"+account.getUserEmail());
        userAuthService.signupProcess(account);
        return resp.toString();
    }

    @PostMapping("/sendverifycode")
    public String sendCode(@RequestBody String userEmail){
        log.info("用户 {} 申请邮箱验证码",userEmail);
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        verifyCodeService.sendEmail("Code:"+userEmail);
        log.info("获取验证码成功！");
        return resp.toString();
    }

}
