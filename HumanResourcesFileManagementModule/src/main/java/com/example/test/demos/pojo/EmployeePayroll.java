package com.example.test.demos.pojo;

import lombok.Data;

@Data
public class EmployeePayroll {

    private Integer epid;
    private Integer dpid;
    private String ename; //员工姓名
    private Integer sid; //员工薪酬标准id

    private Integer total;
}
