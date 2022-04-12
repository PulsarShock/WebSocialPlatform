package com.zlz.websocialplatform.entity.Account;

public class Account {
    String UserEmail;
    String Password;
    String UserName;


    public String getUserEmail() {
        return UserEmail;
    }

    public Account setUserEmail(String userEmail) {
        UserEmail = userEmail;
        return this;
    }

    public String getPassword() {
        return Password;
    }

    public Account setPassword(String password) {
        Password = password;
        return this;
    }

    public String getUserName() {
        return UserName;
    }

    public Account setUserName(String userName) {
        UserName = userName;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"UserEmail\": \"" + UserEmail + '\"' +
                ", \"Password\": \"" + Password + '\"' +
                ", \"UserName\": \"" + UserName + '\"' +
                "}";
    }
}
