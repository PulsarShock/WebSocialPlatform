package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.Post;
import com.zlz.websocialplatform.exception.BaseProjectException;
import com.zlz.websocialplatform.exception.ExceptionEnum;
import com.zlz.websocialplatform.mapper.PostAndCommentsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

public class PACServiceImpl implements PACService{

    @Resource
    PostAndCommentsMapper mapper;

    @Override
    public List<Integer> getPostsList(String userEmail) {
        return mapper.getPostsList(userEmail);
    }

    @Override
    public Post getOnePost(int identity) {
        return mapper.getOnePost(identity);
    }

    @Override
    @Transactional
    public String checkWhetherUpedOrDowned(int identity, String userEmail,String table) {
        List<String> upList= Arrays.asList(mapper.getUpsOrDownsList("up",table,identity).split(","));
        if(upList.contains(userEmail)){
            return "up";
        }
        List<String> downList= Arrays.asList(mapper.getUpsOrDownsList("down",table,identity).split(","));
        if(downList.contains(userEmail)){
            return "down";
        }
        return "none";
    }

    @Override
    public Comment getOneComment(int identity) {
        return mapper.getOneComment(identity);
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
