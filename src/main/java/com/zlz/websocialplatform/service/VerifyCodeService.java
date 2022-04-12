package com.zlz.websocialplatform.service;

public interface VerifyCodeService {

    void sendEmail(String userEmail);

    boolean isCodeExisted(String userEmail);

    boolean checkCode(String userEmail,String verifyCode);
}
