package com.example.test.demos.servicer;

import com.example.test.demos.pojo.Permission;

import java.util.List;

public interface PermissionServicer {
    String addPermission(Permission permission);

    String deletePermission(Integer permissionId);

    String updatePermission(Permission permission);

    List<Permission> getPermissions();
}
