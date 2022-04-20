package com.zlz.websocialplatform.entity.Account;

public class NameAndToken {
    String user_name;
    String user_token;

    public String getUser_name() {
        return user_name;
    }

    public NameAndToken setUser_name(String user_name) {
        this.user_name = user_name;
        return this;
    }

    public String getUser_token() {
        return user_token;
    }

    public NameAndToken setUser_token(String user_token) {
        this.user_token = user_token;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user_name\": \"" + user_name + '\"' +
                ", \"user_token\": \"" + user_token + '\"' +
                "}";
    }
}
