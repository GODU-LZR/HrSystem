package com.example.salarymanage.pojo;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DepartmentPayroll {

    private Integer dpid;
    private String onedepartment; //一级部门
    private String twodepartment; //二级部门
    private String threedepartment; //三级部门
    private Integer number; //部门下的员工数量
    private Integer departmentsalary;
    private Timestamp dtime;
    private String register;
    private Integer checked;
    private List<EmployeePayroll> employeePayrolls; //部门下的所有员工信息

}
