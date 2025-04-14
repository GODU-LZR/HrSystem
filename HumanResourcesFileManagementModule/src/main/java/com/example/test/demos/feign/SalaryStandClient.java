package com.example.test.demos.feign;

import com.example.test.demos.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(name = "salaryStandClient", url = "http://121.43.159.232:8082/api", configuration = FeignConfig.class)
public interface SalaryStandClient {

    @GetMapping("/getSidAndSname")
    Map<Integer, String> getSidAndSname();

}
