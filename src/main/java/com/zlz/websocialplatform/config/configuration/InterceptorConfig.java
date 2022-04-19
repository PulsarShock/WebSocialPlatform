package com.zlz.websocialplatform.config.configuration;

import com.zlz.websocialplatform.config.intercepter.ApiVisitInterceptor;
import com.zlz.websocialplatform.config.intercepter.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public ApiVisitInterceptor apiVisitInterceptor(){
        return new ApiVisitInterceptor();
    }

    @Bean
    public PageInterceptor pageInterceptor(){
        return new PageInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(apiVisitInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/auth/login","/api/auth/logout","/api/auth/signup","/api/auth/sendverifycode"
                );
        registry
                .addInterceptor(pageInterceptor())
                .addPathPatterns("/**/*.html")
                .excludePathPatterns(
                        "/index.html","/form-login.html","/form-signup.html"
                );
    }

}
