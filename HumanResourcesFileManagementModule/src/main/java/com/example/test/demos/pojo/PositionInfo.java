package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("职位信息实体类")
public class PositionInfo {

    @ApiModelProperty("职位ID")
    private Integer positionId;

    @ApiModelProperty("职位类型")
    private String positionType;

    @ApiModelProperty("职位名称")
    private String positionName;

    @ApiModelProperty("父级ID(0表示职位类型,其他值关联职位类型的positionId)")
    private Integer parentId;

    @ApiModelProperty("排序号")
    private Integer positionSort;

    @ApiModelProperty("状态:1-启用,0-禁用")
    private Integer isEnabled;
}
