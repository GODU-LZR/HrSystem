package com.example.test.demos.controller;

import com.example.test.demos.feign.SalaryStandClient;
import com.example.test.demos.pojo.DepartmentPayroll;
import com.example.test.demos.pojo.Employee;
import com.example.test.demos.pojo.EmployeePayroll;
import com.example.test.demos.pojo.Organization;
import com.example.test.demos.servicer.EmployeeServicer;
import com.example.test.demos.servicer.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SessionController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private EmployeeServicer employeeServicer;

    @Autowired
    private SalaryStandClient salaryStandClient;

    /**
     * 根据部门ID返回对应级别部门的雇员集合
     * @param id 部门ID
     * @return 雇员集合
     */
    @GetMapping("/user/{id}")
    public DepartmentPayroll getDepartmentPayrollById(@PathVariable("id") Integer id) {
        // 1. 查询当前部门信息
        Organization currentOrg = organizationService.getOrganizationById(id);
        DepartmentPayroll departmentPayroll = new DepartmentPayroll();
        if (currentOrg == null) {
            throw new IllegalArgumentException("未找到指定部门ID: " + id);
        }

        List<Organization> departmentList = new ArrayList<>();

        // 2. 根据部门级别获取子部门、孙部门或本部门
        switch (currentOrg.getOrgLevel()) {
            case 1: // 一级部门：查询所有子部门和孙部门
                departmentPayroll.setOnedepartment(currentOrg.getOrgName());
                List<Organization> level2Orgs = organizationService.getOrganizationsByLevelAndParentId(2, id);
                departmentList.addAll(level2Orgs); // 添加二级部门

                for (Organization level2Org : level2Orgs) {
                    List<Organization> level3Orgs = organizationService.getOrganizationsByLevelAndParentId(3, level2Org.getOrgId());
                    departmentList.addAll(level3Orgs); // 添加三级部门
                }
                break;

            case 2: // 二级部门：查询所有子部门
                departmentPayroll.setTwodepartment(currentOrg.getOrgName());
                departmentList = organizationService.getOrganizationsByLevelAndParentId(3, id);
                break;

            case 3: // 三级部门：仅查询本部门
                departmentPayroll.setThreedepartment(currentOrg.getOrgName());
                departmentList.add(currentOrg);
                break;

            default:
                throw new IllegalArgumentException("无效的部门级别: " + currentOrg.getOrgLevel());
        }

        // 3. 遍历部门集合，获取雇员信息
        List<Employee> employeeList = new ArrayList<>();
        for (Organization org : departmentList) {
            Employee conditionOne = new Employee();
            conditionOne.setOrgLevel1(org.getOrgName()); // 假设orgName对应员工表中的orgLevel1字段
            List<Employee> employeesOne = employeeServicer.selectByConditions(conditionOne);
            employeeList.addAll(employeesOne);

            Employee conditionTwo = new Employee();
            conditionTwo.setOrgLevel2(org.getOrgName()); // 假设orgName对应员工表中的orgLevel2字段
            List<Employee> employeesTwo = employeeServicer.selectByConditions(conditionTwo);
            employeeList.addAll(employeesTwo);

            Employee conditionThree = new Employee();
            conditionThree.setOrgLevel3(org.getOrgName()); // 假设orgName对应员工表中的orgLevel3字段
            List<Employee> employeesThree = employeeServicer.selectByConditions(conditionThree);
            employeeList.addAll(employeesThree);

        }

        // 4. 去重后返回雇员集合
        List<Employee> collect = employeeList.stream().distinct().collect(Collectors.toList());
        departmentPayroll.setNumber(collect.size());

        Map<Integer, String> sidAndSname = salaryStandClient.getSidAndSname();
        List<EmployeePayroll> employeePayrolls = new ArrayList<>();
        for (Employee employee : collect) {
            System.out.println(employee);
            EmployeePayroll employeePayroll = new EmployeePayroll();
            employeePayroll.setEname(employee.getName());
            employeePayroll.setSid(getKeyByValue(sidAndSname,employee.getSalaryStander()));
            employeePayrolls.add(employeePayroll);
        }
        departmentPayroll.setEmployeePayrolls(employeePayrolls);
        return departmentPayroll;
    }

    public Integer getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey(); // 找到匹配的 value，返回对应的 key
            }
        }
        return null; // 如果没有找到，返回 null
    }

}
