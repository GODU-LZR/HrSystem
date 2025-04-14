package com.example.test.demos.controller;

import com.example.test.demos.pojo.UserDTO;
import com.example.test.demos.servicer.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public String addUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) return "添加失败";
        else return userService.addUser(userDTO);
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }

    @ApiOperation(value = "更新用户信息")
    @PutMapping
    public String updateUser(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @ApiOperation(value = "获取所有用户（支持模糊查询）")
    @GetMapping
    public List<UserDTO> getAllUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {
        return userService.getAllUsers(username, email);
    }

    @ApiOperation(value = "根据ID获取用户信息")
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }


    @ApiOperation(value = "用户退出登录")
    @PostMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        // 清除会话中的用户信息
        request.getSession().invalidate();
        return "退出成功";
    }
}
