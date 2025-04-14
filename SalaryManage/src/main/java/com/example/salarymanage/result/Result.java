package com.example.salarymanage.result;

import lombok.Data;

@Data
public class Result<E> {
    public Integer code;
    public String message;
    public E data;

    public Result(Integer code, String message, E data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Integer Result_code_ok = 200;
    public static Integer Result_code_error = 500;
}
