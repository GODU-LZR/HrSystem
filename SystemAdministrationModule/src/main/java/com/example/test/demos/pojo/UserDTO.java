package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户数据传输对象")
public class UserDTO {

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

    @ApiModelProperty("职位名称")
    private String positionName;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String username, String password, String email, Integer roleId, String positionName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                ", positionName='" + positionName + '\'' +
                '}';
    }
}
