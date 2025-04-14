package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户角色权限关系实体类")
public class UserRolePermission {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("权限ID列表")
    private List<Integer> permissionIds; // 使用List存储多个权限ID

    public UserRolePermission() {
    }

    public UserRolePermission(Integer userId, Integer roleId, List<Integer> permissionIds) {
        this.userId = userId;
        this.roleId = roleId;
        this.permissionIds = permissionIds;
    }

    @Override
    public String toString() {
        return "UserRolePermission{userId=" + userId +
                ", roleId=" + roleId +
                ", permissionIds=" + permissionIds + "}";
    }
}
