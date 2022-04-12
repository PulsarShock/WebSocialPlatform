package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;
import com.zlz.websocialplatform.mapper.UserAuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class UserAuthServiceImpl implements UserAuthService  {

    @Resource
    BCryptPasswordEncoder encoder;
    @Resource
    UserAuthMapper mapper;
    @Resource
    VerifyCodeService verifyCodeService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    private ValueOperations<String,String> operations;

    @PostConstruct
    private void setOperations(){
        this.operations = stringRedisTemplate.opsForValue();
    }

    public boolean isUserEmailMatchesPwd(String userEmail, String Pwd) {
        String CryptPwd = mapper.getPwdByEmail(userEmail);
        return encoder.matches(Pwd,CryptPwd);
    }

    public boolean isUserEmailExists(String userEmail) {
        return mapper.containsEmail(userEmail)!=null;
    }

    public boolean isTokenValid(String userEmail, String user_token) {
        return user_token.equals(operations.get(userEmail));
    }

    public void createUserAccount(String userEmail, String password, String name) {
        mapper.createUser(userEmail,encoder.encode(password),name);
    }

    @Override
    public String loginProcess(Account account) {
        if(isUserEmailMatchesPwd(account.getUserEmail(),account.getPassword())){
            account.setUserEmail("Token"+account.getUserEmail());
            if(operations.get(account.getUserEmail())!=null){
                //TODO:这里我想实现一下单点登录，判断出该用户已登录时，新的登录直接把他踢下去，移除key，同时撤销那个session的授权，再重新写入key和新的token
                stringRedisTemplate.delete(account.getUserEmail());
            }
            String user_token= DigestUtils.md5DigestAsHex((account.getUserEmail()+account.getPassword()+new Date()).getBytes(StandardCharsets.UTF_8));
            operations.set(account.getUserEmail(), user_token);
            return user_token;
        }
        else{
            throw new RuntimeException("403");
        }

    }

    @Override
    public void signupProcess(AccountForSignup account) {
        if(isUserEmailExists(account.getUserEmail())){
            log.info("邮箱 {} 已注册！",account.getUserEmail().substring(4));
            throw new RuntimeException("100401");
        }
        if(verifyCodeService.checkCode(account.getUserEmail(), account.getVerifyCode())){
            createUserAccount(account.getUserEmail(), account.getPassword(), account.getUserName());
            log.info("{} 成功注册！",account.getUserEmail().substring(4));
        }
        log.info("{} 验证码错误！",account.getUserEmail().substring(4));
        throw new RuntimeException("100402");
    }

    @Override
    public void logoutProcess(String userEmail,String user_token) {
        if(!isTokenValid(userEmail, user_token)){
            throw new RuntimeException("401");
        }
        stringRedisTemplate.delete(userEmail);
    }
}
