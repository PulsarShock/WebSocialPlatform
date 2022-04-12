package com.zlz.websocialplatform;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.entity.User;
import com.zlz.websocialplatform.mapper.UserAuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class WebSocialPlatformApplicationTests {

    @Resource
    UserAuthMapper mapper;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        //mapper.createUser("1@qq",encoder.encode("111"));
        List<User> list = new ArrayList<>();
        list.add(new User("nnn","aaa",12));
        list.add(new User("www","aarrra",123));
        list.add(new User("eee","aatttda",41));
        //RestBean<List<User>> r=new RestBean<>(200,"hh",list);
        RestBean<User> r=new RestBean<>(200,"hh",new User("nnn","aaa",12));
        RestBean<String> r2=new RestBean<>(200,"oo","ppp");
        log.info(r.toString());
        log.info(r2.toString());
    }

}
