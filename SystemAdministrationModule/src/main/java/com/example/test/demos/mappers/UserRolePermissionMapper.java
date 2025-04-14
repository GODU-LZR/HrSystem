package com.example.test.demos.mappers;

import com.example.test.demos.pojo.UserRolePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserRolePermissionMapper {

    // 插入用户角色权限关系
    @Insert({
            "<script>",
            "INSERT INTO UserRolePermission(userId, roleId, permissionId) VALUES",
            "<foreach collection='permissionIds' item='permissionId' separator=','>",
            "(#{userId}, #{roleId}, #{permissionId})",
            "</foreach>",
            "</script>"
    })
    void insertUserRolePermissions(@Param("userId") Integer userId,
                                   @Param("roleId") Integer roleId,
                                   @Param("permissionIds") List<Integer> permissionIds);

    default void insertUserRolePermission(UserRolePermission urp) {
        insertUserRolePermissions(urp.getUserId(), urp.getRoleId(), urp.getPermissionIds());
    }

    // 根据 roleId 和 userId 查询权限ID列表
    @Select("SELECT permissionId FROM UserRolePermission WHERE roleId=#{roleId} AND userId=#{userId}")
    List<Integer> selectPermissionIdsByRoleIdAndUserId(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

    @Select("SELECT permissionId FROM UserRolePermission WHERE roleId=#{roleId}")
    List<Integer> selectPermissionIdsByRoleId(@Param("roleId") Integer roleId);

    // 根据 userId 删除权限关联
    @Delete("DELETE FROM UserRolePermission WHERE userId=#{userId}")
    void deleteByUserId(@Param("userId") Integer userId);

    // 根据 roleId 删除权限关联
    @Delete("DELETE FROM UserRolePermission WHERE roleId=#{roleId} AND userId=0")
    void deleteByRoleId(@Param("roleId") Integer roleId);

    // 根据 userId 查询该用户所有权限ID列表
    @Select("SELECT permissionId FROM UserRolePermission WHERE userId=#{userId}")
    List<Integer> selectPermissionIdsByUserId(@Param("userId") Integer userId);  // 新增方法
}
