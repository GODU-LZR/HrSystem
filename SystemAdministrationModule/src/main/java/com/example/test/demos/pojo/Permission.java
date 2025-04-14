package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权限信息实体类")
public class Permission {
    @ApiModelProperty("权限ID")
    private Integer permissionId;

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限描述")
    private String description;

    public Permission() {
    }

    public Permission(Integer permissionId, String permissionName, String description) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Permission{permissionId=" + permissionId + ", permissionName='" + permissionName + "', description='" + description + "'}";
    }
}
