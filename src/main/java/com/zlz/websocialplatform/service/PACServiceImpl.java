package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.MyInteger;
import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.Post;
import com.zlz.websocialplatform.entity.exception.BaseProjectException;
import com.zlz.websocialplatform.entity.exception.ExceptionEnum;
import com.zlz.websocialplatform.mapper.PostAndCommentsMapper;
import com.zlz.websocialplatform.mapper.UserAuthMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class PACServiceImpl implements PACService{

    @Resource
    PostAndCommentsMapper mapper;
    @Resource
    UserAuthMapper userAuthMapper;

    @Override
    public List<MyInteger> getPostsList(String userEmail) {
        log.info(String.valueOf(mapper.getPostsList(userEmail).size()));
        return mapper.getPostsList("1784456958@qq.com");
    }

    @Override
    public Post getOnePost(int identity) {
        Post post=mapper.getOnePost(identity);
        post.setUserName(userAuthMapper.getUserName(post.getUserEmail()));
        return post;
    }

    @Override
    @Transactional
    public String checkWhetherUpedOrDowned(int identity, String userEmail,String table) {
        String list=mapper.getUpsOrDownsList("up",table,identity);
        List<String> upList= list!=null?Arrays.asList(list.split(",")):null;
        if(upList!=null&&upList.contains(userEmail)){
            return "up";
        }
        list=mapper.getUpsOrDownsList("down",table,identity);
        List<String> downList=list!=null?Arrays.asList(list.split(",")):null;
        if(downList!=null&&downList.contains(userEmail)){
            return "down";
        }
        return "none";
    }

    @Override
    public Comment getOneComment(int identity) {
        Comment comment=mapper.getOneComment(identity);
        comment.setUserName(userAuthMapper.getUserName(comment.getUserEmail()));
        return comment;
    }

    @Override
    @Transactional
    public void changeState(int identity,String confirm, String cancel,String table,String userEmail) {
        //TODO:这里要改为redis缓存方案
        if(table.equals("posts")&&mapper.getOnePost(identity)==null){
            throw new BaseProjectException(ExceptionEnum.NOT_FOUND);
        }
        if(table.equals("comments")&&mapper.getOneComment(identity)==null){
            throw new BaseProjectException(ExceptionEnum.NOT_FOUND);
        }
        if(!confirm.equals("no_confirmed")){
            if(confirm.equals("up")){
                int ups= mapper.getUpsOrDowns("up",table,identity);
                String newStr=mapper.getUpsOrDownsList("up", table, identity)+","+userEmail;
                mapper.setUpsOrDownsList("up",table,identity,newStr);
                mapper.setUpsOrDowns("up",table,identity,ups+1);
            }
            else {
                int downs= mapper.getUpsOrDowns("down",table,identity);
                String newStr=mapper.getUpsOrDownsList("down", table, identity)+","+userEmail;
                mapper.setUpsOrDownsList("down",table,identity,newStr);
                mapper.setUpsOrDowns("down",table,identity,downs+1);
            }
        }
        else if(!cancel.equals("no_canceled")){
            if(cancel.equals("up")){
                int ups= mapper.getUpsOrDowns("up",table,identity);
                mapper.setUpsOrDowns("up",table,identity,ups-1);
                String list=mapper.getUpsOrDownsList("up",table,identity);
                Set<String> set=new HashSet<>();
                Collections.addAll(set,list.split(","));
                set.remove(userEmail);
                mapper.setUpsOrDownsList("up",table,identity,StringUtils.join(set.toArray(),","));
            }
            else {
                int downs= mapper.getUpsOrDowns("down",table,identity);
                mapper.setUpsOrDowns("down",table,identity,downs-1);
                String list=mapper.getUpsOrDownsList("down",table,identity);
                Set<String> set=new HashSet<>();
                Collections.addAll(set,list.split(","));
                set.remove(userEmail);
                mapper.setUpsOrDownsList("down",table,identity,StringUtils.join(set.toArray(),","));
            }
        }
    }
}
