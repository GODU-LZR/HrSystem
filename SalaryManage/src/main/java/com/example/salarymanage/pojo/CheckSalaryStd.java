package com.example.salarymanage.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class CheckSalaryStd {

    private Integer checkid; //复核条目id
    private Integer sid; //薪资标准id
    private String sname; //薪资标准名称
    private Integer total; //薪资标准总额
    private String maker; //制定人
    private String register; //登记人
    private Timestamp stime; //登记时间
    private String opinion; //复核意见
    private Integer variety; //复核变更条目
    private Integer checked; //是否通过复核
}
