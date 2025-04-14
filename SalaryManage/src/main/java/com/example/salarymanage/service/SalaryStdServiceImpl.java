package com.example.salarymanage.service;

import com.example.salarymanage.mapper.SalaryStdMapper;
import com.example.salarymanage.parameter.SidAndSname;
import com.example.salarymanage.pojo.CheckSalaryStd;
import com.example.salarymanage.parameter.RemoveSalaryStd;
import com.example.salarymanage.pojo.SalaryStd;
import com.example.salarymanage.parameter.SelectSalaryStd;
import com.example.salarymanage.service.service.SalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SalaryStdServiceImpl implements SalaryStdService {

    @Autowired
    public SalaryStdMapper salaryStdMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Integer getId(){
        try{
            boolean iscreate = salaryStdMapper.createId();
            return salaryStdMapper.getId();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean addSalaryStd(CheckSalaryStd checkSalaryStd) {
        return salaryStdMapper.addSalaryStd(checkSalaryStd);
    }

    @Override
    public boolean modifySalaryStd(CheckSalaryStd checkSalaryStd) {
        return salaryStdMapper.modifySalaryStd(checkSalaryStd);
    }

    @Override
    public boolean removeSalaryStd(RemoveSalaryStd removeSalaryStd) {
        return salaryStdMapper.removeSalaryStd(removeSalaryStd);
    }

    @Override
    public List<SalaryStd> getSalaryStd(SelectSalaryStd selectSalaryStd) {
        return salaryStdMapper.getSalaryStd(selectSalaryStd);
    }

    @Override
    public Map<Integer, String> getAllSidAndSname(){
        List<SidAndSname> list = salaryStdMapper.getAllSidAndSname();
        return list.stream()
                .collect(Collectors.toMap(
                        SidAndSname::getSid,
                        item -> item.getSname() != null ? item.getSname() : "无" // 替换 null 为默认值
                ));
    }
}
