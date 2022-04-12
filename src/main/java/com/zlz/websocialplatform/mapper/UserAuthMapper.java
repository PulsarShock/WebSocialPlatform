package com.zlz.websocialplatform.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserAuthMapper {

    @Select("select pwd from user_accounts where user_email=#{userEmail}")
    String getPwdByEmail(@Param("userEmail") String userEmail);

    @Insert("insert into user_accounts(user_email,pwd,user_name) value(#{email},#{pwd},#{name})")
    void createUser(@Param("email") String userEmail ,@Param("pwd") String CryptPwd ,@Param("name") String name);

    @Select("select user_email from user_accounts where user_email=#{userEmail}")
    String containsEmail(@Param("userEmail") String userEmail);
}
