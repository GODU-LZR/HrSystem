package com.example.salarymanage.feign;

import com.example.salarymanage.config.FeignConfig;
import com.example.salarymanage.pojo.DepartmentPayroll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "issueSalaryFeign", url = "http://121.43.159.232:8081/api", configuration = FeignConfig.class)
public interface IssueSalaryFeign {

    @GetMapping("/user/{id}")
    public DepartmentPayroll getDepartmentPayrollById(@PathVariable("id") Integer id);


}
