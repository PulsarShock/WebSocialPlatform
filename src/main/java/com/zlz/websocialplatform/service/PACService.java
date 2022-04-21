package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.PostAndComments.Comment;
import com.zlz.websocialplatform.entity.PostAndComments.Post;

import java.util.List;

public interface PACService {

    List<Integer> getPostsList(String userEmail);

    Post getOnePost(int identity);

    String checkWhetherUpedOrDowned(int identity,String userEmail,String table);

    Comment getOneComment(int identity);

    void changeState(int identity,String confirm,String cancel,String table,String userEmail);

}
