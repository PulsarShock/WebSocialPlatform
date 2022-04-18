package com.zlz.websocialplatform.config.configuration;

import com.zlz.websocialplatform.config.intercepter.ApiVisitInterceptor;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(apiVisitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/auth/login","/api/auth/logout","/api/auth/signup","/api/auth/sendverifycode",
                        "/error/**",
                        "/index.html","/form-login.html","/form-signup.html",
                        "/**/*.css","/**/*.js","/**/*.ttf","/**/*.woff","/**/*.png","/**/*.jpg","/**/*.svg","/**/*.ico"
                );
    }

}
