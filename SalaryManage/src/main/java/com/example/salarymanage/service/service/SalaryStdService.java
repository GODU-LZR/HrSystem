package com.example.salarymanage.service.service;

import com.example.salarymanage.pojo.CheckSalaryStd;
import com.example.salarymanage.parameter.RemoveSalaryStd;
import com.example.salarymanage.pojo.SalaryStd;
import com.example.salarymanage.parameter.SelectSalaryStd;

import java.util.List;
import java.util.Map;

public interface SalaryStdService {

    //创建并获取预定薪资标准条目
    public Integer getId();

    //新增薪资标准
    public boolean addSalaryStd(CheckSalaryStd checkSalaryStd);

    //修改薪资标准
    public boolean modifySalaryStd(CheckSalaryStd checkSalaryStd);

    //删除薪资标准
    public boolean removeSalaryStd(RemoveSalaryStd removeSalaryStd);

    //查询薪资标准
    public List<SalaryStd> getSalaryStd(SelectSalaryStd selectSalaryStd);

    //查询所有的薪资标准id和薪资标准名称
    public Map<Integer, String> getAllSidAndSname();
}
