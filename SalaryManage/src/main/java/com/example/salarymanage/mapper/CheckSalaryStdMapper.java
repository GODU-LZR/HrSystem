package com.example.salarymanage.mapper;

import com.example.salarymanage.parameter.SelectCheckSalaryStd;
import com.example.salarymanage.pojo.CheckSalaryStd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckSalaryStdMapper {

    //获取一条未审核的复核薪资标准变更请求
    public CheckSalaryStd getCheckSalaryStd();

    //复核完成后删除对应的复核条目
    public boolean removeCheckSalaryStd(@Param("checkid") Integer checkid, @Param("checked") Integer checked);

    //同意复核薪资标准增加请求
    public boolean checkaddSalaryStd(@Param("checkid") Integer checkid, @Param("checker") String checker);

    //同意复核薪资标准修改请求
    public boolean checkmodifySalaryStd(@Param("checkid") Integer checkid, @Param("checker") String checker);

    //同意复核薪资标准删除请求
    public boolean checkremoveSalaryStd(@Param("checkid") Integer checkid, @Param("checker") String checker);

    //获取所有的复核薪资标准请求
    public List<CheckSalaryStd> getCheckSalaryStd(SelectCheckSalaryStd selectCheckSalaryStd);

    //根据id获取复核薪资标准请求
    public CheckSalaryStd getCheckSalaryStdById(Integer checkid);
}
