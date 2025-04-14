package com.example.salarymanage.feign;


import com.example.salarymanage.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "userInfoClient", url = "http://121.43.159.232:8080/api", configuration = FeignConfig.class)
public interface UserInfoClient {

    @GetMapping("/userinfo")
    Map<String, Object> getUserInfo();
}
