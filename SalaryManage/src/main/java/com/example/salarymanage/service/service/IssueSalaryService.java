package com.example.salarymanage.service.service;

import com.example.salarymanage.parameter.SelectDepartmentPayroll;
import com.example.salarymanage.pojo.DepartmentPayroll;
import com.example.salarymanage.pojo.EmployeePayroll;

import java.util.List;

public interface IssueSalaryService {

    //登记部门薪酬发放表和部门员工薪酬发放表
    public Integer addIssueSalary(Integer id);

    //根据已知数据获取到部门薪酬发放表
    public List<DepartmentPayroll> getDepartmentPayroll(SelectDepartmentPayroll selectDepartmentPayroll);

    //根据部门薪酬发放表id获取到部门员工薪酬发放表
    public List<EmployeePayroll> getIssueSalaryInEmployeeByDpid(Integer dpid);

    //复核部门薪酬发放表
    public boolean checkIssueSalary(Integer dpid, Integer isagree);
}
