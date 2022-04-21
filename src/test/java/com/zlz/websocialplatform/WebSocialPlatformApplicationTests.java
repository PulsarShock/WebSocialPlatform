package com.zlz.websocialplatform;

import com.zlz.websocialplatform.mapper.PostAndCommentsMapper;
import com.zlz.websocialplatform.mapper.UserAuthMapper;
import com.zlz.websocialplatform.utils.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class WebSocialPlatformApplicationTests {

    @Resource
    UserAuthMapper mapper;
    @Resource
    PostAndCommentsMapper postAndCommentsMapper;

    @Test
    void contextLoads() {
        int i=10;
        while (i>0){
            //postAndCommentsMapper.createPost("1784456958@qq.com",MyUtils.randomCode(),MyUtils.dateOfNow());
            postAndCommentsMapper.createComment("1784456958@qq.com",1000009, MyUtils.randomCode(),MyUtils.dateOfNow());
            i--;
        }
        System.out.println("success!");
    }

    @Test
    void ttt(){
        //System.out.println(postAndCommentsMapper.getOnePost(1000009));
        //System.out.println(postAndCommentsMapper.getCommentsIDsOfPost(1000009));
        System.out.println(postAndCommentsMapper.getUpsOrDownsList("up","posts",1000004));
        System.out.println(postAndCommentsMapper.getUpsOrDownsList("down","posts",1000004));
    }

}
