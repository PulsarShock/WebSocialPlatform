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
        System.out.println(JSON.parseObject("'777'"));
    }

}
