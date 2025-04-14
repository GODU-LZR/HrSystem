package com.example.test.demos.servicer;

import com.example.test.demos.pojo.User;
import com.example.test.demos.pojo.UserDTO;

import java.util.List;

public interface UserService {
    String addUser(UserDTO userDTO);

    String deleteUser(Integer userId);

    String updateUser(UserDTO userDTO);

    List<UserDTO> getAllUsers(String username, String email);

    UserDTO getUserById(Integer userId);

    String loginUser(String username, String password);

    String registerUser(User user);

    // 通过用户ID查询该用户的权限ID列表
    List<Integer> getPermissionsByUsername(String username);

    UserDTO getUserByUsername(String username);

}

