package com.zlz.websocialplatform.service;

import com.zlz.websocialplatform.entity.Account.Account;
import com.zlz.websocialplatform.entity.Account.AccountForSignup;

public interface UserAuthService {

    String loginProcess(Account account);

    void signupProcess(AccountForSignup account);

    void logoutProcess(String userEmail, String user_token);

}