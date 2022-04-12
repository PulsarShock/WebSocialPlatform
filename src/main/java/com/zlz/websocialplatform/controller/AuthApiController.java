package com.zlz.websocialplatform.controller;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;
import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.service.UserAuthService;
import com.zlz.websocialplatform.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/api/auth")
public class AuthApiController {
    //TODO:使用aop环绕，每次请求前都检验user_token

    @Resource
    UserAuthService userAuthService;
    @Resource
    VerifyCodeService verifyCodeService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String userInfo){
        log.info("登录用户信息：{}",userInfo);
        Account account = JSON.parseObject(userInfo).toJavaObject(Account.class);
        RestBean<String> resp=new RestBean<>(200,"");
        try {
            String token=userAuthService.loginProcess(account);
            return resp.setReason("login success!").setData(token).toString();
        }catch (RuntimeException e){
            return resp.setCode(403).setReason("login failed!").toString();
        }
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(@RequestHeader("user_email") String logoutUserEmail,@RequestHeader("user_token") String token){
        log.info("用户 {} 申请登出",logoutUserEmail);
        RestBean<Void> resp=new RestBean<>(200,"");
        try{
            userAuthService.logoutProcess("Token"+logoutUserEmail,token);
        }catch (RuntimeException e){
            log.info("用户 {} 请求登出的操作未通过权限认证！",logoutUserEmail);
            return resp.setCode(401).setReason("invalid token!").toString();
        }
        log.info("用户 {} 登出成功！",logoutUserEmail);
        return resp.setReason("logout success").toString();
    }

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody String signupInfo){
        AccountForSignup account=JSON.parseObject(signupInfo).toJavaObject(AccountForSignup.class);
        log.info("注册用户信息：{}",account.toString());
        RestBean<Void> resp=new RestBean<>(200,"");
        try {
            //我用两种字符串前缀对code队列和token队列做了区分
            account.setUserEmail("Code"+account.getUserEmail());
            userAuthService.signupProcess(account);
            return resp.setReason("signup success!").toString();
        }catch (RuntimeException e){
            if("100401".equals(e.getMessage())){
                log.info("{}所请求的邮箱{}已被注册！",account.getUserName(),account.getUserEmail().substring(4));
                return resp.setCode(100401).setReason("used emailaddress!").toString();
            }
            log.info("{} 验证码错误！",account.getUserName());
            return resp.setCode(100402).setReason("wrong verifycode!").toString();
        }
    }

    @PostMapping("/sendverifycode")
    @ResponseBody
    public String sendCode(@RequestBody String userEmail){
        log.info("用户 {} 申请邮箱验证码",userEmail);
        RestBean<Void> resp=new RestBean<>(200,"");
        try {
            //我用两种字符串前缀对code队列和token队列做了区分
            //但是这里不加，在verifycodeservice中加，因为还要发邮件
            verifyCodeService.sendEmail(userEmail);
            log.info("获取验证码成功！");
            return resp.setReason("get code success!").toString();
        }
        catch (RuntimeException e){
            if(e.getMessage().equals("100403")){
                log.info("验证码尚未过期!");
                return resp.setCode(100403).setReason("verifycode still exists!").toString();
            }
            log.info("获取验证码失败!");
            return resp.setCode(100404).setReason("get code failed!").toString();
        }
    }

}
