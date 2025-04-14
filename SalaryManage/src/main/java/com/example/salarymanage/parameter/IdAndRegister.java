package com.example.salarymanage.parameter;

import lombok.Data;

@Data
public class IdAndRegister {

    private Integer sid; //薪资标准id
    private String register; //登记人

    public IdAndRegister(Integer sid, String register) {
        this.sid = sid;
        this.register = register;
    }
}
