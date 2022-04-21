package com.zlz.websocialplatform;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.CommentForFront;
import com.zlz.websocialplatform.entity.PostAndComments.Post;
import com.zlz.websocialplatform.entity.PostAndComments.PostForFront;
import com.zlz.websocialplatform.entity.RestBean;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainTest {

    static class inner{
        String mm;


    }

    @Test
    public void time(){
        System.out.println(new SimpleDateFormat("yyyy-MM-hh HH:mm:ss").format(new Date()));
    }

    @Test
    public void tt(){
        System.out.println(JSON.parseObject("{'mm':'hhh'}").toJavaObject(inner.class).mm);
    }

}
