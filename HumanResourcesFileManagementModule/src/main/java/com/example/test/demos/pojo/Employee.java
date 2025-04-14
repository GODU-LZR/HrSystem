package com.example.test.demos.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("员工信息实体类")
public class Employee {

    @ApiModelProperty("I级机构")
    private String orgLevel1;           // I级机构

    @ApiModelProperty("II级机构")
    private String orgLevel2;           // II级机构

    @ApiModelProperty("III级机构")
    private String orgLevel3;           // III级机构

    @ApiModelProperty("职位分类")
    private String positionCategory;    // 职位分类

    @ApiModelProperty("职位名称")
    private String positionName;        // 职位名称

    @ApiModelProperty("薪酬标准 (外包/合同)")
    private String salaryStander;       // 薪酬标准 (外包/合同)

    @ApiModelProperty("员工照片 URL")
    private String employeePhotoUrl;    // 员工照片 URL

    @ApiModelProperty("姓名")
    private String name;                // 姓名

    @ApiModelProperty("性别 (男/女)")
    private String gender;              // 性别 (男/女)

    @ApiModelProperty("Email")
    private String email;               // Email

    @ApiModelProperty("电话")
    private String phone;               // 电话

    @ApiModelProperty("QQ")
    private String qq;                  // QQ

    @ApiModelProperty("手机")
    private String mobile;              // 手机

    @ApiModelProperty("住址")
    private String address;             // 住址

    @ApiModelProperty("邮编")
    private String postalCode;          // 邮编

    @ApiModelProperty("国籍")
    private String nationality;         // 国籍

    @ApiModelProperty("生日")
    private LocalDateTime birthday;     // 生日

    @ApiModelProperty("民族")
    private String ethnicity;           // 民族

    @ApiModelProperty("宗教信仰")
    private String religion;            // 宗教信仰

    @ApiModelProperty("政治面貌")
    private String politicalAffiliation;// 政治面貌

    @ApiModelProperty("身份证号码")
    private String idNumber;            // 身份证号码

    @ApiModelProperty("社会保障号码")
    private String socialSecurityNumber;// 社会保障号码

    @ApiModelProperty("年龄")
    private Integer age;                // 年龄

    @ApiModelProperty("学历 (小学/初中/高中/大专/本科及以上)")
    private String education;           // 学历

    @ApiModelProperty("账号")
    private String account;             // 账号

    @ApiModelProperty("建档时间")
    private LocalDateTime registerTime; // 建档时间

    @ApiModelProperty("个人履历")
    private String resume;              // 个人履历

    @ApiModelProperty("家庭关系信息")
    private String familyInfo;          // 家庭关系信息

    @ApiModelProperty("备注")
    private String remarks;             // 备注

    @ApiModelProperty("复核状态 0 待复核 1 已复核")
    private Integer reviewId;           // 复核状态 0 待复核 1 已复核

    @ApiModelProperty("档案唯一Id")
    private Integer archiveId;          // 档案唯一Id

    @ApiModelProperty("删除状态 0 未删除 1已删除")
    private Integer isdeleted;          // 删除状态 0 未删除 1已删除

    @ApiModelProperty("登记人名称")
    private String Registrant;          // 登记人名称

    @ApiModelProperty("变更人名称")
    private String changePerson;        // 变更人名称

    @ApiModelProperty("复核人名称")
    private String reviewer;            // 复核人名称

    @ApiModelProperty("截至时间")
    private LocalDateTime deadlineTime; // 截至时间用于查询

}
