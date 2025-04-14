package com.example.salarymanage.service;

import com.example.salarymanage.mapper.CheckSalaryStdMapper;
import com.example.salarymanage.parameter.SelectCheckSalaryStd;
import com.example.salarymanage.pojo.CheckSalaryStd;
import com.example.salarymanage.service.service.CheckSalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckSalaryStdServiceImpl implements CheckSalaryStdService {

    @Autowired(required = false)
    private CheckSalaryStdMapper checkSalaryStdMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean checkSalaryStd(Integer checkid, String checker, Integer isagree) {
        CheckSalaryStd checkSalaryStd = checkSalaryStdMapper.getCheckSalaryStdById(checkid);
        if(checkSalaryStd == null || checkSalaryStd.getChecked() != 0){
            return false;
        }
        if(isagree == 0){ //表示复核不通过
            checkSalaryStdMapper.removeCheckSalaryStd(checkid, 2); //2表示状态为"已复核:不通过"
            return true;
        }
        Integer variety = checkSalaryStd.getVariety();
        if(variety == 1){
            checkSalaryStdMapper.checkaddSalaryStd(checkid, checker);
        }else if(variety == 2){
            checkSalaryStdMapper.checkmodifySalaryStd(checkid, checker);
        }else if(variety == 3){
            checkSalaryStdMapper.checkremoveSalaryStd(checkid, checker);
        }
        checkSalaryStdMapper.removeCheckSalaryStd(checkid, 1);
        return true;
    }

    @Override
    public List<CheckSalaryStd> getCheckSalaryStd(SelectCheckSalaryStd selectCheckSalaryStd) {
        return checkSalaryStdMapper.getCheckSalaryStd(selectCheckSalaryStd);
    }

    @Override
    public CheckSalaryStd getCheckSalaryStdById(Integer checkid){
        return checkSalaryStdMapper.getCheckSalaryStdById(checkid);
    }
}
