package com.example.salarymanage.utils;


import com.example.salarymanage.parameter.SelectSalaryStd;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Untils {

    public static Timestamp StringToTimestamp(String time){
        if(time == null || time.equals("")){
            return null;
        }
        // 使用 LocalDate 解析日期字符串
        LocalDate localDate = LocalDate.parse(time);
        // 将 LocalDate 转换为 java.sql.Timestamp
        return Timestamp.valueOf(localDate.atStartOfDay());
    }


    // 处理时间字段的通用方法，合并了处理逻辑
    public static void processTimeFields(SelectSalaryStd selectSalaryStd) {
        // 处理 start_time 和 end_time 两个时间字段
        if (selectSalaryStd.getStart_time() != null) {
            selectSalaryStd.setStart_time(removeTimezoneOffset(selectSalaryStd.getStart_time()));
        }
        if (selectSalaryStd.getEnd_time() != null) {
            selectSalaryStd.setEnd_time(removeTimezoneOffset(selectSalaryStd.getEnd_time()));
        }
    }

    // 去掉时区偏移并将时分秒设置为零的辅助方法
    private static Timestamp removeTimezoneOffset(Timestamp timestamp) {
        // 如果 timestamp 为 null，直接返回 null，不进行任何处理
        if (timestamp == null) {
            return null;
        }

        // 获取原始时间的 LocalDateTime
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        // 将时分秒设置为零（确保时间为 00:00:00）
        LocalDateTime localDateTimeAtStartOfDay = localDateTime.toLocalDate().atStartOfDay();

        // 转换为 Timestamp
        return Timestamp.valueOf(localDateTimeAtStartOfDay);
    }


}
