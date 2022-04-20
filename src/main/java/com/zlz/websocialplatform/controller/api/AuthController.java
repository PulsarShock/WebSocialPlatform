package com.zlz.websocialplatform.controller.api;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;
import com.zlz.websocialplatform.entity.Account.NameAndToken;
import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.service.UserAuthService;
import com.zlz.websocialplatform.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Resource
    UserAuthService userAuthService;
    @Resource
    VerifyCodeService verifyCodeService;

    @PostMapping("/login")
    public String login(@RequestBody String userInfo, HttpSession session){
        log.info("登录用户信息：{}",userInfo);
        Account account = JSON.parseObject(userInfo).toJavaObject(Account.class);
        RestBean<NameAndToken> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        account.setUserEmail(account.getUserEmail()+":Token:"+session.getId());
        NameAndToken nat=new NameAndToken();
        String token=userAuthService.loginProcess(account).get("user_token");
        String name=userAuthService.loginProcess(account).get("user_name");
        nat.setUser_name(name).setUser_token(token);
        return resp.setData(nat).toString();
    }

    @GetMapping("/logout")
    public String logout(@RequestParam("user_email") String logoutUserEmail,@RequestParam("user_token") String token,HttpSession  session){
        log.info("用户 {} 申请登出",logoutUserEmail);
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        userAuthService.logoutProcess(logoutUserEmail+":Token:"+session.getId(),token);
        log.info("用户 {} 登出成功！",logoutUserEmail);
        return resp.toString();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody String signupInfo){
        AccountForSignup account=JSON.parseObject(signupInfo).toJavaObject(AccountForSignup.class);
        log.info("注册用户信息：{}",account.toString());
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        account.setUserEmail(account.getUserEmail()+":Code");
        userAuthService.signupProcess(account);
        return resp.toString();
    }

    @PostMapping("/send_verify_code")
    public String sendCode(@RequestBody String userEmail){
        log.info("用户 {} 申请邮箱验证码",userEmail);
        RestBean<Void> resp=new RestBean<>(200,"success!");
        //我用两种字符串前缀对code队列和token队列做了区分
        verifyCodeService.sendEmail(userEmail+":Code");
        log.info("获取验证码成功！");
        return resp.toString();
    }

}
