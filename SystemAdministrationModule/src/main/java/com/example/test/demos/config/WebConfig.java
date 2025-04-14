package com.example.test.demos.config;

import com.example.test.demos.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/",  // 根路径
                        "/login", "/register",  // 登录和注册接口
                        "/public/**",  // 静态资源
                        "/api/userinfo",  // 放行获取用户信息接口
                        "/user/logout"  // 放行退出登录接口
                );
    }

}
