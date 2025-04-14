package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("机构信息实体类")
public class Organization {

    @ApiModelProperty("机构ID")
    private Integer orgId;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("机构层级:1-一级,2-二级,3-三级")
    private Integer orgLevel;

    @ApiModelProperty("父级机构ID")
    private Integer parentId;

    @ApiModelProperty("排序号")
    private Integer orgSort;

    @ApiModelProperty("状态:1-启用,0-禁用")
    private Integer status;

    // 如果需要显示父级机构名称，可以添加以下属性（非数据库字段）
    @ApiModelProperty("父级机构名称")
    private String parentName;
}
