package com.zlz.websocialplatform.entity.PostAndComments;

import java.util.List;

public class Comment {
    int commentID;
    String userEmail;
    String userName;
    int postID;
    String content;
    int ups;
    List<String> upsList;
    int downs;
    List<String> downsList;
    String timeStamp;

    public int getCommentID() {
        return commentID;
    }

    public Comment setCommentID(int commentID) {
        this.commentID = commentID;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Comment setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public int getPostID() {
        return postID;
    }

    public Comment setPostID(int postID) {
        this.postID = postID;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public int getUps() {
        return ups;
    }

    public Comment setUps(int ups) {
        this.ups = ups;
        return this;
    }

    public List<String> getUpsList() {
        return upsList;
    }

    public Comment setUpsList(List<String> upsList) {
        this.upsList = upsList;
        return this;
    }

    public int getDowns() {
        return downs;
    }

    public Comment setDowns(int downs) {
        this.downs = downs;
        return this;
    }

    public List<String> getDownsList() {
        return downsList;
    }

    public Comment setDownsList(List<String> downsList) {
        this.downsList = downsList;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Comment setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Comment setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
