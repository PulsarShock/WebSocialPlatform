package com.zlz.websocialplatform.entity.PostAndComments;

import java.util.List;

public class PostForFront {
    int identity;
    String user_email;
    String user_name;
    String content;
    UpsAndDowns ups_downs;
    String comment_closed;
    List<Integer> comments;
    String time_stamp;

    public PostForFront(Post post,String activated){
        this.identity=post.postID;
        this.user_email=post.userEmail;
        this.user_name=post.userName;
        this.content=post.content;
        this.ups_downs=new UpsAndDowns(post.ups,post.downs,activated);
        this.comment_closed=post.commentClosed;
        this.comments=post.comments;
        this.time_stamp=post.timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"identity\":" + identity +
                ", \"user_email\": \"" + user_email + '\"' +
                ", \"user_name\": \"" + user_name + '\"' +
                ", \"content\": \"" + content + '\"' +
                ", \"ups_downs\":" + ups_downs +
                ", \"comment_closed\": \"" + comment_closed + '\"' +
                ", \"comments\":" + comments +
                ", \"time_stamp\": \"" + time_stamp + '\"' +
                "}";
    }
}
