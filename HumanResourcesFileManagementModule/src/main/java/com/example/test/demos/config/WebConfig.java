package com.example.test.demos.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public WebConfig(@Lazy AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")  // 所有请求都需要通过拦截器
                .excludePathPatterns(
                        "/",  // 根路径
                        "/login", "/register",  // 登录和注册接口
                        "/public/**",  // 静态资源
                        "/api/userinfo",  // 放行获取用户信息接口
                        "/user/logout"  // 放行退出登录接口
                );
    }
}
