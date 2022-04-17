package com.zlz.websocialplatform.config.configuration;

import com.zlz.websocialplatform.config.intercepter.ApiVisitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new ApiVisitInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/login","/api/logout","/api/signup","/api/sendverifycode");
    }

}
