package com.example.test.demos.mappers;

import com.example.test.demos.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    // 插入用户
    @Insert("INSERT INTO User (username, password, email, roleId) VALUES (#{username}, #{password}, #{email}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    void insert(User user);

    // 根据 userId 删除用户
    @Delete("DELETE FROM User WHERE userId = #{userId}")
    void deleteByUserId(Integer userId);

    // 更新用户信息
    @Update("<script>" +
            "UPDATE User" +
            "<set>" +
            "<if test='username != null'>username = #{username},</if>" +
            "<if test='password != null'>password = #{password},</if>" +
            "<if test='email != null'>email = #{email},</if>" +
            "<if test='roleId != null'>roleId = #{roleId}</if>" +
            "</set>" +
            "WHERE userId = #{userId}" +
            "</script>")
    void update(User user);

    // 查询所有用户，支持模糊查询
    @Select("<script>" +
            "SELECT userId, username, email, roleId FROM User" +
            "<where>" +
            "<if test='username != null'>AND username LIKE CONCAT('%', #{username}, '%')</if>" +
            "<if test='email != null'>AND email LIKE CONCAT('%', #{email}, '%')</if>" +
            "</where>" +
            "</script>")
    List<User> selectAllWithFuzzySearch(@Param("username") String username, @Param("email") String email);

    // 根据 userId 查询用户
    @Select("SELECT userId, username, password, email, roleId FROM User WHERE userId = #{userId}")
    User selectByUserId(Integer userId);

    // 根据用户名查询用户
    @Select("SELECT userId, username, password, email, roleId FROM User WHERE username = #{username}")
    User selectByUsername(String username);

    // 根据邮箱查询用户
    @Select("SELECT userId, username, password, email, roleId FROM User WHERE email = #{email}")
    User selectByEmail(String email);
}
