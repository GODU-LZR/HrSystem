package com.example.test.demos.controller;

import com.example.test.demos.pojo.User;
import com.example.test.demos.servicer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    // 登录接口
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (username == null || password == null) {
            return "登录失败：用户名或密码不能为空";
        }

        String result = userService.loginUser(username, password);
        if ("登录成功".equals(result)) {
            // 登录成功，将用户信息存入 Session
            session.setAttribute("user", username);
        }
        return result;
    }

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) {
            return "注册失败：用户名、密码或邮箱不能为空";
        }

        return userService.registerUser(user);
    }
}
