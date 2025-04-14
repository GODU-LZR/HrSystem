package com.example.salarymanage.parameter;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SelectSalaryStd {

    private Integer sid;
    private String key;
    private Timestamp start_time;
    private Timestamp end_time;

}
