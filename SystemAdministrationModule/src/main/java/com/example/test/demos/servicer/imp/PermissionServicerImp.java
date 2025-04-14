package com.example.test.demos.servicer.imp;

import com.example.test.demos.mappers.PermissionMapper;
import com.example.test.demos.pojo.Permission;
import com.example.test.demos.servicer.PermissionServicer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServicerImp implements PermissionServicer {

    @Autowired(required = false)
    private PermissionMapper permissionMapper;

    @Override
    public String addPermission(Permission permission) {
        permissionMapper.insert(permission);
        System.out.println("s "+permission);
        return "权限添加成功";
    }

    @Override
    public String deletePermission(Integer permissionId) {
        permissionMapper.delete(permissionId);
        return "权限删除成功";
    }

    @Override
    public String updatePermission(Permission permission) {
        permissionMapper.update(permission);
        return "权限更新成功";
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissions = permissionMapper.selectAll();
        for (Permission permission : permissions) {
            System.out.println(permission);
        }
        return permissions;
    }
}
