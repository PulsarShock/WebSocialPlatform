package com.zlz.websocialplatform.controller.api;

import com.alibaba.fastjson.JSON;
import com.zlz.websocialplatform.entity.MyInteger;
import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.CommentForFront;
import com.zlz.websocialplatform.entity.PostAndComments.Post;
import com.zlz.websocialplatform.entity.PostAndComments.PostForFront;
import com.zlz.websocialplatform.entity.RestBean;
import com.zlz.websocialplatform.service.PACService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts_and_comments")
public class PostAndCommentsController {

    static class userInfo1{
        String user_email;
        int identity;
        public userInfo1 setUser_email(String user_email) {this.user_email = user_email;return this;}
        public userInfo1 setIdentity(int identity) {this.identity = identity;return this;}
    }

    static class userInfo2{
        String table;
        int identity;
        String user_email;
        String confirm;
        String cancel;
        public userInfo2 setTable(String table) {this.table = table;return this;}
        public userInfo2 setIdentity(int identity) {this.identity = identity;return this;}
        public userInfo2 setUser_email(String user_email) {this.user_email = user_email;return this;}
        public userInfo2 setConfirm(String confirm) {this.confirm = confirm;return this;}
        public userInfo2 setCancel(String cancel) {this.cancel = cancel;return this;}
    }

    List<String> list= Arrays.asList(
            "/get_posts_list",
            "/get_single_post",
            "/get_single_comment",
            "/change_up_down_state",
            "/shutdown_comments",
            "/delete",
            "/reply",
            "/new_post"
    );

    @Resource
    PACService pacService;

    @PostMapping("/get_posts_list")
    public String postsList(@RequestBody String userEmail){
        log.info("用户 {} 请求了动态列表",userEmail);
        List<MyInteger> list=pacService.getPostsList(userEmail);
        log.info(list.toString());
        return new RestBean<List<MyInteger>>().setData(list).toString();
    }

    @PostMapping("/get_single_post")
    public String onePost(@RequestBody String userInfo){
        userInfo1 userInfo1=JSON.parseObject(userInfo).toJavaObject(userInfo1.class);
        log.info("用户 {} 请求了id为 {} 的动态",userInfo1.user_email,userInfo1.identity);
        Post post=pacService.getOnePost(userInfo1.identity);
        String activated=pacService.checkWhetherUpedOrDowned(userInfo1.identity,userInfo1.user_email,"posts");
        return new RestBean<PostForFront>().setData(new PostForFront(post,activated)).toString();
    }

    @PostMapping("/get_single_comment")
    public String oneComment(@RequestBody String userInfo){
        userInfo1 userInfo1=JSON.parseObject(userInfo).toJavaObject(userInfo1.class);
        log.info("用户 {} 请求了id为 {} 的评论",userInfo1.user_email,userInfo1.identity);
        Comment comment=pacService.getOneComment(userInfo1.identity);
        String activated=pacService.checkWhetherUpedOrDowned(userInfo1.identity,userInfo1.user_email,"comments");
        return new RestBean<CommentForFront>().setData(new CommentForFront(comment,activated)).toString();
    }

    @PostMapping("/change_up_down_state")
    public String change(@RequestBody String userInfo){
        userInfo2 userInfo2=JSON.parseObject(userInfo).toJavaObject(userInfo2.class);
        if(!userInfo2.confirm.equals("no_confirmed")){
            log.info("用户 {} 对 {} "+userInfo2.confirm+"了一下",userInfo2.user_email,userInfo2.identity);
        }
        else if(!userInfo2.cancel.equals("no_canceled")){
            log.info("用户 {} 对 {} 取消了"+userInfo2.cancel,userInfo2.user_email,userInfo2.identity);
        }
        pacService.changeState(userInfo2.identity,userInfo2.confirm,userInfo2.cancel,userInfo2.table,userInfo2.user_email);
        return new RestBean<Void>().toString();
    }

}
