package com.example.test.demos.servicer.imp;

import com.example.test.demos.mappers.RoleMapper;
import com.example.test.demos.mappers.UserMapper;
import com.example.test.demos.mappers.UserRolePermissionMapper;
import com.example.test.demos.pojo.Role;
import com.example.test.demos.pojo.User;
import com.example.test.demos.pojo.UserDTO;
import com.example.test.demos.pojo.UserRolePermission;
import com.example.test.demos.servicer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserService 实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private RoleMapper roleMapper;

    @Autowired(required = false)
    private UserRolePermissionMapper userRolePermissionMapper;

    @Override
    public String addUser(UserDTO userDTO) {
        // 1. 将 UserDTO 转换为 User 实体
        User user = new User();
        user.setUsername(userDTO.getUsername());
        // 需要对密码进行加密处理
        user.setPassword(userDTO.getPassword()); // 在此处实现密码加密
        user.setEmail(userDTO.getEmail());
        user.setRoleId(userDTO.getRoleId());

        // 2. 插入用户
        userMapper.insert(user);

        // 3. 处理 UserRolePermission
        // 从 UserRolePermission 表中获取 userId=0 且 roleId=指定值的权限列表
        List<Integer> permissionIds = userRolePermissionMapper.selectPermissionIdsByRoleIdAndUserId(user.getRoleId(), 0);

        if (permissionIds != null && !permissionIds.isEmpty()) {
            // 为新用户分配权限
            UserRolePermission urp = new UserRolePermission();
            urp.setUserId(user.getUserId());
            urp.setRoleId(user.getRoleId());
            urp.setPermissionIds(permissionIds);
            userRolePermissionMapper.insertUserRolePermission(urp);
        }

        return "用户添加成功";
    }

    @Override
    public String deleteUser(Integer userId) {
        // 删除用户
        userMapper.deleteByUserId(userId);

        // 删除用户权限关联
        userRolePermissionMapper.deleteByUserId(userId);

        return "用户删除成功";
    }

    @Override
    public String updateUser(UserDTO userDTO) {
        // 获取现有用户信息
        User existingUser = userMapper.selectByUserId(userDTO.getUserId());
        if (existingUser == null) {
            return "用户不存在";
        }

        // 更新用户信息
        existingUser.setUsername(userDTO.getUsername());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword()); // 在此处实现密码加密
        }
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRoleId(userDTO.getRoleId());

        // 更新用户
        userMapper.update(existingUser);

        // 更新用户权限关联
        // 首先删除原有的权限关联
        userRolePermissionMapper.deleteByUserId(userDTO.getUserId());

        // 获取新角色对应的权限列表
        List<Integer> permissionIds = userRolePermissionMapper.selectPermissionIdsByRoleIdAndUserId(existingUser.getRoleId(), 0);

        if (permissionIds != null && !permissionIds.isEmpty()) {
            // 为用户分配新的权限
            UserRolePermission urp = new UserRolePermission();
            urp.setUserId(existingUser.getUserId());
            urp.setRoleId(existingUser.getRoleId());
            urp.setPermissionIds(permissionIds);
            userRolePermissionMapper.insertUserRolePermission(urp);
        }

        return "用户更新成功";
    }

    @Override
    public List<UserDTO> getAllUsers(String username, String email) {
        // 获取用户列表，支持模糊查询
        List<User> users = userMapper.selectAllWithFuzzySearch(username, email);
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(user.getUserId());
            userDTO.setUsername(user.getUsername());
            // 不返回密码字段，确保安全
            userDTO.setEmail(user.getEmail());
            userDTO.setRoleId(user.getRoleId());

            // 获取职位名称
            Role role = roleMapper.selectRoleById(user.getRoleId());
            if (role != null) {
                userDTO.setPositionName(role.getRoleName());
            }

            userDTOs.add(userDTO);
        }

        return userDTOs;
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user = userMapper.selectByUserId(userId);
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        // 不返回密码字段
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleId(user.getRoleId());

        // 获取职位名称
        Role role = roleMapper.selectRoleById(user.getRoleId());
        if (role != null) {
            userDTO.setPositionName(role.getRoleName());
        }

        return userDTO;
    }

    // 实现登录方法
    @Override
    public String loginUser(String username, String password) {
        // 查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return "登录失败：用户不存在";
        }

        // 比较密码（实际应用中应使用加密密码）
        if (!user.getPassword().equals(password)) {
            return "登录失败：密码错误";
        }

        return "登录成功";
    }

    // 实现注册方法
    @Override
    public String registerUser(User user) {
        // 检查用户名和邮箱是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return "注册失败：用户名已存在";
        }

        existingUser = userMapper.selectByEmail(user.getEmail());
        if (existingUser != null) {
            return "注册失败：邮箱已被注册";
        }

        // 设置默认角色ID为7（普通用户）
        user.setRoleId(7);

        // 插入用户
        userMapper.insert(user);

        // 获取自动生成的 userId
        Integer userId = user.getUserId();

        // 复制权限：从 userId=0 且 roleId=7 的记录中获取权限列表
        List<Integer> permissionIds = userRolePermissionMapper.selectPermissionIdsByRoleIdAndUserId(7, 0);

        if (permissionIds != null && !permissionIds.isEmpty()) {
            // 为新用户分配权限
            UserRolePermission urp = new UserRolePermission();
            urp.setUserId(userId);
            urp.setRoleId(7);
            urp.setPermissionIds(permissionIds);
            userRolePermissionMapper.insertUserRolePermission(urp);
        }

        return "注册成功";
    }


    @Override
    public List<Integer> getPermissionsByUsername(String username) {
        // 根据用户名查询用户ID
        User user = userMapper.selectByUsername(username);
        System.out.println("userId+  "+user.getUserId());

        if (user != null) {
            // 根据用户ID查询权限列表
            return userRolePermissionMapper.selectPermissionIdsByUserId(user.getUserId());
        }

        // 如果用户名不存在，返回空列表
        return new ArrayList<>();
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return null;
    }

}
