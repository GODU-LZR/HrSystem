package com.example.test.demos.mappers;

import com.example.test.demos.pojo.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PermissionMapper {

    @Insert("INSERT INTO Permission (permissionName, description) VALUES (#{permissionName}, #{description})")
    void insert(Permission permission);

    @Delete("DELETE FROM Permission WHERE permissionId = #{permissionId}")
    void delete(Integer permissionId);

    @Update("UPDATE Permission SET permissionName = #{permissionName}, description = #{description} WHERE permissionId = #{permissionId}")
    void update(Permission permission);

    @Select("SELECT permissionId, permissionName, description FROM Permission")
    List<Permission> selectAll();

    @Select("SELECT permissionId, permissionName, description FROM Permission WHERE permissionId = #{permissionId}")
    Permission selectById(Integer permissionId);
}
