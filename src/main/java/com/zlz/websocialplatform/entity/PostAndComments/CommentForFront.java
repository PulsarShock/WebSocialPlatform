package com.zlz.websocialplatform.entity.PostAndComments;

public class CommentForFront {
    int identity;
    String user_email;
    String user_name;
    String content;
    UpsAndDowns ups_downs;
    String time_stamp;

    public CommentForFront(Comment comment,String activated){
        this.identity=comment.commentID;
        this.user_email=comment.userEmail;
        this.user_name=comment.userName;
        this.content=comment.content;
        this.ups_downs=new UpsAndDowns(comment.ups,comment.downs,activated);
        this.time_stamp=comment.timeStamp;
    }

    @Override
    public String toString() {
        return "{" +
                "\"identity\":" + identity +
                ", \"user_email\": \"" + user_email + '\"' +
                ", \"user_name\": \"" + user_name + '\"' +
                ", \"content\": \"" + content + '\"' +
                ", \"ups_downs\":" + ups_downs +
                ", \"time_stamp\": \"" + time_stamp + '\"' +
                "}";
    }
}
