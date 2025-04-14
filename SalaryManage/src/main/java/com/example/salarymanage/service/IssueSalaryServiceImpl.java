package com.example.salarymanage.service;

import com.example.salarymanage.feign.IssueSalaryFeign;
import com.example.salarymanage.mapper.IssueSalaryMapper;
import com.example.salarymanage.parameter.SelectDepartmentPayroll;
import com.example.salarymanage.pojo.DepartmentPayroll;
import com.example.salarymanage.pojo.EmployeePayroll;
import com.example.salarymanage.service.service.IssueSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueSalaryServiceImpl implements IssueSalaryService {

    @Autowired(required = false)
    private IssueSalaryMapper issueSalaryMapper;

    @Autowired
    private IssueSalaryFeign issueSalaryFeign;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    //登记部门薪酬发放表和部门员工薪酬发放表
    public Integer addIssueSalary(Integer id) {

//        DepartmentPayroll departmentPayroll = new DepartmentPayroll();
//        departmentPayroll.setOnedepartment("I部门");
//        departmentPayroll.setTwodepartment("II部门");
//
//        List<EmployeePayroll> employeePayrolls1 = new ArrayList<>();
//        EmployeePayroll employeePayroll1 = new EmployeePayroll();
//        employeePayroll1.setEname("科比·布莱恩特");
//        employeePayroll1.setSid(1);
//        employeePayrolls1.add(employeePayroll1);
//        departmentPayroll.setEmployeePayrolls(employeePayrolls1);
        DepartmentPayroll departmentPayroll = issueSalaryFeign.getDepartmentPayrollById(id);
        if(departmentPayroll == null){
            return -1;
        }
        System.out.println(departmentPayroll);
        try{
            //获取到部门员工薪酬发放集合
            List<EmployeePayroll> employeePayrolls = departmentPayroll.getEmployeePayrolls();
            //将部门薪酬发放表添加到数据库(此时薪酬总额未知)
            issueSalaryMapper.addIssueSalaryInDepartment(departmentPayroll);
            //获取到刚刚添加的部门薪酬发放表的编号dpid
            Integer dpid = issueSalaryMapper.getNewIssueSalaryInDepartment();
            //为该部门员工的所属部门薪酬发放表赋值
            employeePayrolls.stream().forEach(employeePayroll -> employeePayroll.setDpid(dpid));
            //将部门员工薪酬发放表添加到数据库中
            issueSalaryMapper.addIssueSalaryInEmployee(employeePayrolls);
            //更新部门薪酬发放表的总额
            issueSalaryMapper.updateDepartmentSalary(dpid);
            return dpid;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    //根据已知数据获取到部门薪酬发放表
    public List<DepartmentPayroll> getDepartmentPayroll(SelectDepartmentPayroll selectDepartmentPayroll) {
        return issueSalaryMapper.getDepartmentPayroll(selectDepartmentPayroll);
    }

    @Override
    //根据部门薪酬发放表id获取到部门员工薪酬发放表
    public List<EmployeePayroll> getIssueSalaryInEmployeeByDpid(Integer dpid) {
        return issueSalaryMapper.getIssueSalaryInEmployeeByDpid(dpid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    //复核部门薪酬发放表
    public boolean checkIssueSalary(Integer dpid, Integer isagree) {
        try{
            //复核不通过
            if(isagree == 0){
                //将部门薪酬发放表状态置为2，表示复核未通过
                issueSalaryMapper.modifyIssueSalaryInDepartment(dpid, 2);
                //删除该部门员工薪资方法表
                issueSalaryMapper.removeIssueSalaryInEmployeeByDpid(dpid);
            }else{ //复核通过
                //将部门薪酬发放表状态置为1，表示复核通过
                issueSalaryMapper.modifyIssueSalaryInDepartment(dpid, 1);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
