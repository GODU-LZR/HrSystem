package com.example.salarymanage.controller;

import com.example.salarymanage.annotations.RequiresPermissions;
import com.example.salarymanage.parameter.Check;
import com.example.salarymanage.parameter.RemoveSalaryStd;
import com.example.salarymanage.parameter.SelectDepartmentPayroll;
import com.example.salarymanage.pojo.DepartmentPayroll;
import com.example.salarymanage.pojo.EmployeePayroll;
import com.example.salarymanage.result.Result;
import com.example.salarymanage.service.service.IssueSalaryService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/IssueSalaryController")
@ResponseBody
public class IssueSalaryController {

    @Autowired
    private IssueSalaryService issueSalaryService;

    //登记部门薪酬发放表和部门员工薪酬发放表
    @RequiresPermissions({17}) // 需要权限ID 17
    @RequestMapping(value = "/addIssueSalary")
    public Result<Integer> addIssueSalary(@RequestParam("id") Integer id){
        Integer flag = issueSalaryService.addIssueSalary(id);
        if(flag != null){
            return new Result<Integer>(200, "添加成功", flag);
        }else {
            return new Result<Integer>(500, "添加失败", null);
        }
    }

    //根据已知数据获取到部门薪酬发放表
    @RequiresPermissions({17}) // 需要权限ID 17
    @RequestMapping(value = "/getDepartmentPayroll")
    public Result<List<DepartmentPayroll>> getDepartmentPayroll(@RequestBody SelectDepartmentPayroll selectDepartmentPayroll){
        List<DepartmentPayroll> departmentPayrolls = issueSalaryService.getDepartmentPayroll(selectDepartmentPayroll);
        return new Result<>(200, "部门薪资发放表查询成功", departmentPayrolls);
    }

    //根据部门薪酬发放表id获取到部门员工薪酬发放表
    @RequiresPermissions({17}) // 需要权限ID 17
    @RequestMapping(value = "/getIssueSalaryInEmployeeByDpid")
    public Result<List<EmployeePayroll>> getIssueSalaryInEmployeeByDpid(@RequestParam("dpid") Integer dpid) {
        System.out.println(dpid);
        List<EmployeePayroll> employeePayrolls = issueSalaryService.getIssueSalaryInEmployeeByDpid(dpid);
        return new Result<>(200, "部门员工薪资发放表查询成功", employeePayrolls);
    }

    //复核部门薪酬发放表
    @RequiresPermissions({17}) // 需要权限ID 17
    @RequestMapping(value = "/checkIssueSalary")
    public Result<String> checkIssueSalary(@RequestBody Check check){
        boolean flag = issueSalaryService.checkIssueSalary(check.getCheckid(), check.getIsagree());
        if(flag){
            return new Result<String>(200, "复核成功", null);
        }else {
            return new Result<String>(500, "复核失败", null);
        }
    }
}
