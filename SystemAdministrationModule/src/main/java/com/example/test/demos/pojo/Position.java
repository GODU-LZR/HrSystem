package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("职位信息实体类")
public class Position {

    @ApiModelProperty("职位ID")
    private Integer positionId;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("职位描述")
    private String description;

    @ApiModelProperty("关联的权限ID列表")
    private List<Integer> permissionIds; // 用于存储职位关联的权限ID列表

    @ApiModelProperty("关联的权限名称列表")
    private List<String> permissionNames; // 新增，用于返回权限名称列表

    public Position() {
    }

    public Position(Integer positionId, String positionName, String description, List<Integer> permissionIds, List<String> permissionNames) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.description = description;
        this.permissionIds = permissionIds;
        this.permissionNames = permissionNames;
    }

    @Override
    public String toString() {
        return "Position{" +
                "positionId=" + positionId +
                ", positionName='" + positionName + '\'' +
                ", description='" + description + '\'' +
                ", permissionIds=" + permissionIds +
                ", permissionNames=" + permissionNames +
                '}';
    }
}
