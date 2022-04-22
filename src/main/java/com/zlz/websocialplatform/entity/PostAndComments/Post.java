package com.zlz.websocialplatform.entity.PostAndComments;

import com.zlz.websocialplatform.entity.MyInteger;

import java.util.List;

public class Post {
    int postID;
    String userEmail;
    String userName;
    String content;
    int ups;
    List<String> upsList;
    int downs;
    List<String> downsList;
    String commentClosed;
    List<MyInteger> comments;
    String timeStamp;

    public int getPostID() {
        return postID;
    }

    public Post setPostID(int postID) {
        this.postID = postID;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Post setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public int getUps() {
        return ups;
    }

    public Post setUps(int ups) {
        this.ups = ups;
        return this;
    }

    public List<String> getUpsList() {
        return upsList;
    }

    public Post setUpsList(List<String> upsList) {
        this.upsList = upsList;
        return this;
    }

    public int getDowns() {
        return downs;
    }

    public Post setDowns(int downs) {
        this.downs = downs;
        return this;
    }

    public List<String> getDownsList() {
        return downsList;
    }

    public Post setDownsList(List<String> downsList) {
        this.downsList = downsList;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Post setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getCommentClosed() {
        return commentClosed;
    }

    public Post setCommentClosed(String commentClosed) {
        this.commentClosed = commentClosed;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Post setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public List<MyInteger> getComments() {
        return comments;
    }

    public Post setComments(List<MyInteger> comments) {
        this.comments = comments;
        return this;
    }
}
