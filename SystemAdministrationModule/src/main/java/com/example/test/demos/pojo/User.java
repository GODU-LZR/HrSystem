package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("用户信息实体类")
public class User {
    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    public User() {
    }

    public User(Integer userId, String username, String password, String email, Integer roleId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{userId=" + userId + ", username='" + username + "', email='" + email + "', roleId=" + roleId + "}";
    }
}
