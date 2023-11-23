package com.AiTongue;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 翰林猿
 * @Description: 拦截器的配置
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new UserLoginInterceptor());
        interceptorRegistration.addPathPatterns("/**");         //拦截全部的路径
        interceptorRegistration.excludePathPatterns("/user/login","/user/register","/getStaticImg/*");    //但是注册和登录不要拦截
    }
}
