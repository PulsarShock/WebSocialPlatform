package com.zlz.websocialplatform.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyCodeServiceImpl implements VerifyCodeService{

    @Resource
    JavaMailSender sender;
    @Resource
    StringRedisTemplate template;

    private ValueOperations<String, String> operations;

    @PostConstruct
    private void setOperations(){
        this.operations=template.opsForValue();
    }

    @Override
    public void sendEmail(String userEmail) {
        if(isCodeExisted("Code"+userEmail)){
            throw new RuntimeException("100403");
        }
        Random random=new Random();
        String code=String.valueOf(random.nextInt(899999)+100000);
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("赭峦斋平台验证码");
        mailMessage.setText("您的验证码为："+code+"，请在60秒内使用。");
        mailMessage.setTo(userEmail);
        mailMessage.setFrom("2418754520@qq.com");
        try{
            sender.send(mailMessage);
        }catch (Exception e){
            throw new RuntimeException("100404");
        }
        operations.set("Code"+userEmail, code,60, TimeUnit.SECONDS);
    }

    @Override
    public boolean isCodeExisted(String userEmail) {
        return operations.get(userEmail)!=null;
    }

    @Override
    public boolean checkCode(String userEmail, String verifyCode) {
        String code = operations.get(userEmail);
        if(code==null) return false;
        if(code.equals(verifyCode)){
            template.delete(userEmail);
            return true;
        }
        return false;
    }

}