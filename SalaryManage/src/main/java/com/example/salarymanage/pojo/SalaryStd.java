package com.example.salarymanage.pojo;

import io.swagger.models.auth.In;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class SalaryStd {

    private Integer sid; //薪资标准id
    private String sname; //薪资标准名称
    private Integer total; //薪资标准总额
    private String maker; //制定人
    private String register; //登记人
    private Timestamp stime; //登记时间
    private String checker; //复核人
}
