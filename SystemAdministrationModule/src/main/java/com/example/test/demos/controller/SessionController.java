package com.example.test.demos.controller;

import com.example.test.demos.pojo.User;
import com.example.test.demos.servicer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    private UserService userService;

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        // 从 Session 获取当前用户的用户名
        String username = (String) session.getAttribute("user");

        if (username == null) {
            // 用户未登录，返回 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录");
        }

        // 调用 UserService 获取权限列表
        List<Integer> permissions = userService.getPermissionsByUsername(username);
        for (Integer permission : permissions) {
            System.out.println(permission);
        }

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("permissions", permissions);

        return ResponseEntity.ok(result);
    }
}
