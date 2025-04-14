package com.example.salarymanage.service.service;

import com.example.salarymanage.parameter.SelectCheckSalaryStd;
import com.example.salarymanage.pojo.CheckSalaryStd;

import java.util.List;

public interface CheckSalaryStdService {

    //复核薪资标准变更请求
    public boolean checkSalaryStd(Integer checkid, String checker, Integer checked);

    //获取所有的复核薪资标准请求
    public List<CheckSalaryStd> getCheckSalaryStd(SelectCheckSalaryStd selectCheckSalaryStd);

    //根据id获取复核薪资标准请求
    public CheckSalaryStd getCheckSalaryStdById(Integer checkid);
}
