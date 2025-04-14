package com.example.test.demos.mappers;

import com.example.test.demos.pojo.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RoleMapper {

    @Insert("INSERT INTO Role(roleName, description) VALUES(#{roleName}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    void insertRole(Role role);

    @Delete("DELETE FROM Role WHERE roleId=#{roleId}")
    void deleteRole(@Param("roleId") Integer roleId);

    @Update("UPDATE Role SET roleName=#{roleName}, description=#{description} WHERE roleId=#{roleId}")
    void updateRole(Role role);

    @Select("SELECT * FROM Role")
    List<Role> selectAllRoles();

    @Select("SELECT * FROM Role WHERE roleId=#{roleId}")
    Role selectRoleById(@Param("roleId") Integer roleId);
}
