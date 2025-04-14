package com.example.salarymanage.controller;

import com.example.salarymanage.service.service.SalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@ResponseBody
public class SessionController {

    @Autowired
    private SalaryStdService salaryStdService;

    @GetMapping ("/getSidAndSname")
    public Map<Integer, String> getSidAndSname(){
        Map<Integer, String> map = salaryStdService.getAllSidAndSname();
        System.out.println(map);
        return map;
    }


}
