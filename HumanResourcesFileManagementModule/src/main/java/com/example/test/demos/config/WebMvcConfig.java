package com.example.test.demos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有映射
                .allowedOrigins("http://121.43.159.232:8080", "http://121.43.159.232:8081", "http://121.43.159.232:8082") // 允许的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .allowCredentials(true) // 允许发送 Cookie
                .maxAge(3600);
    }
}
