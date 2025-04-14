package com.example.test.demos.controller;

import com.example.test.demos.pojo.Permission;
import com.example.test.demos.servicer.PermissionServicer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionServicer permissionServicer;

    @ApiOperation(value="添加权限")
    @PostMapping
    public String addPermission(@RequestBody Permission permission) {
        return permissionServicer.addPermission(permission);
    }

    @ApiOperation(value="删除权限")
    @DeleteMapping("/{permissionId}")
    public String deletePermission(@PathVariable Integer permissionId) {
        return permissionServicer.deletePermission(permissionId);
    }

    @ApiOperation(value="更新权限")
    @PutMapping
    public String updatePermission(@RequestBody Permission permission) {
        return permissionServicer.updatePermission(permission);
    }

    @ApiOperation(value="获取所有权限")
    @GetMapping
    public List<Permission> getPermissions() {
        return permissionServicer.getPermissions();
    }
}
