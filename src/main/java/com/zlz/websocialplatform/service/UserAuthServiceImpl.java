package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;
import com.zlz.websocialplatform.entity.exception.BaseProjectException;
import com.zlz.websocialplatform.entity.exception.ExceptionEnum;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    protected boolean isUserEmailMatchesPwd(String userEmail, String Pwd) {
        String CryptPwd = mapper.getPwdByEmail(userEmail.split(":")[0]);//mapper使用的email都要去掉前缀
        return encoder.matches(Pwd,CryptPwd);
    }

    protected boolean isUserEmailExists(String userEmail) {
        return mapper.containsEmail(userEmail.split(":")[0])!=null;
    }

    protected boolean isTokenValid(String userEmail, String user_token) {
        return user_token.equals(operations.get(userEmail));
    }

    protected void createUserAccount(String userEmail, String password, String name) {
        mapper.createUser(userEmail.split(":")[0],encoder.encode(password),name);
    }

    protected String getUserName(String userEmail){
        return mapper.getUserName(userEmail.split(":")[0]);
    }

    @Override
    public Map<String,String> loginProcess(Account account) {
        if(isUserEmailMatchesPwd(account.getUserEmail(),account.getPassword())){
            if(operations.get(account.getUserEmail())!=null){
                //如果redis已存在该用户token，那么查找所有email:Token:*模式的key，然后依次移除，这样就能保证新的登录可以把别的会话的认证踢下线
                for (String key : Objects.requireNonNull(stringRedisTemplate.keys(account.getUserEmail().split(":")[0] + ":Token:*"))) {
                    stringRedisTemplate.delete(key);
                }
            }
            String user_token= DigestUtils.md5DigestAsHex((account.getUserEmail()+account.getPassword()+new Date()).getBytes(StandardCharsets.UTF_8));
            operations.set(account.getUserEmail(), user_token,60, TimeUnit.MINUTES);
            String user_name= getUserName(account.getUserEmail());
            Map<String,String> map=new HashMap<>();
            map.put("user_name",user_name);
            map.put("user_token",user_token);
            return map;
        }
        else{
            throw new BaseProjectException(ExceptionEnum.LOGGING_FAILED);
        }
    }

    @Override
    public void signupProcess(AccountForSignup account) {
        if(isUserEmailExists(account.getUserEmail())){
            throw new BaseProjectException(ExceptionEnum.USED_EMAIL_ADDRESS);
        }
        if(verifyCodeService.checkCode(account.getUserEmail(), account.getVerifyCode())){
            createUserAccount(account.getUserEmail(), account.getPassword(), account.getUserName());
            log.info("{} 成功注册！",account.getUserEmail().split(":")[0]);
        }
        throw new BaseProjectException(ExceptionEnum.WRONG_VERIFICATION_CODE);
    }

    @Override
    public void logoutProcess(String userEmail,String user_token) {
        if(!isTokenValid(userEmail, user_token)){
            throw new BaseProjectException(ExceptionEnum.UNAUTHORIZED);
        }
        stringRedisTemplate.delete(userEmail);
    }
}
