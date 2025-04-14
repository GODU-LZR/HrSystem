package com.example.test.demos.controller;

import com.example.test.demos.feign.UserInfoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoClient userInfoClient;

    @GetMapping("/current-username")
    public ResponseEntity<String> getCurrentUsername() {
        try {
            Map<String, Object> userInfo = userInfoClient.getUserInfo();
            if (userInfo.containsKey("username")) {
                String username = (String) userInfo.get("username");
                return ResponseEntity.ok(username);
            } else {
                // 如果Map中没有username字段，说明未登录或获取失败
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录");
            }
        } catch (Exception e) {
            // 调用Feign出现异常可能是401未登录或其他问题
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录");
        }
    }
}
