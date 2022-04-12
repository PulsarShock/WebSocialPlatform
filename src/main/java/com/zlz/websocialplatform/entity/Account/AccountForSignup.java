package com.zlz.websocialplatform.entity.Account;

public class AccountForSignup extends Account{
    String VerifyCode;

    public String getVerifyCode() {
        return VerifyCode;
    }

    public AccountForSignup setVerifyCode(String verifyCode) {
        VerifyCode = verifyCode;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"UserEmail\": \"" + UserEmail + '\"' +
                ", \"Password\": \"" + Password + '\"' +
                ", \"UserName\": \"" + UserName + '\"' +
                ", \"VerifyCode\": \"" + VerifyCode + '\"' +
                "}";
    }
}
