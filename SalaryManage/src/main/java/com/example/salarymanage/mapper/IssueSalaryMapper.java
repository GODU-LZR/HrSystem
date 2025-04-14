package com.example.salarymanage.mapper;

import com.example.salarymanage.parameter.SelectDepartmentPayroll;
import com.example.salarymanage.pojo.DepartmentPayroll;
import com.example.salarymanage.pojo.EmployeePayroll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IssueSalaryMapper {

    //返回新增的部门薪酬登记表dpid
    public Integer getNewIssueSalaryInDepartment();

    //登记部门登记薪酬表
    public void addIssueSalaryInDepartment(DepartmentPayroll departmentPayroll);

    //登记部门员工登记薪酬表
    public void addIssueSalaryInEmployee(@Param("employeePayrolls") List<EmployeePayroll> employeePayrolls);

    //修正部门薪酬发放表的薪酬总额
    public void updateDepartmentSalary(@Param("dpid") Integer dpid);

    //根据部门薪酬登记表id来删除部门员工薪酬方法表
    public void removeIssueSalaryInEmployeeByDpid(Integer dpid);

    //根据信息获取到部门薪酬登记表
    public List<DepartmentPayroll> getDepartmentPayroll(SelectDepartmentPayroll selectDepartmentPayroll);

    //根据部门薪酬登记表id获取到部门员工薪酬发放表
    public List<EmployeePayroll> getIssueSalaryInEmployeeByDpid(@Param("dpid") Integer dpid);

    //修改部门薪酬登记表的复核状态
    public void modifyIssueSalaryInDepartment(@Param("dpid") Integer dpid,@Param("checked") Integer checked);
}
