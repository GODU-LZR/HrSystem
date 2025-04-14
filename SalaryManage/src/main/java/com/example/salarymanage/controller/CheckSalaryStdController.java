package com.example.salarymanage.controller;

import com.example.salarymanage.annotations.RequiresPermissions;
import com.example.salarymanage.feign.UserInfoClient;
import com.example.salarymanage.parameter.Check;
import com.example.salarymanage.parameter.SelectCheckSalaryStd;
import com.example.salarymanage.pojo.CheckSalaryStd;
import com.example.salarymanage.result.Result;
import com.example.salarymanage.service.service.CheckSalaryStdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/CheckSalaryStdController")
@ResponseBody
public class CheckSalaryStdController {

    @Autowired
    private CheckSalaryStdService checkSalaryStdService;

    @Autowired
    private UserInfoClient userInfoClient;

    //根据数据信息获取复核薪资标准请求
    @RequiresPermissions({20}) // 需要权限ID 20
    @RequestMapping(value = "/getCheckSalaryStd")
    public Result<List<CheckSalaryStd>> getCheckSalaryStd(@RequestBody SelectCheckSalaryStd selectCheckSalaryStd) {
        try {
            List<CheckSalaryStd> checkSalaryStds = checkSalaryStdService.getCheckSalaryStd(selectCheckSalaryStd);
            return new Result<List<CheckSalaryStd>>(200, "查询成功", checkSalaryStds);
        } catch (Exception e) {
            return new Result<List<CheckSalaryStd>>(500, "查询失败", null);
        }
    }

    //根据id获取一条复核薪资标准请求
    @RequiresPermissions({20}) // 需要权限ID 20
    @RequestMapping(value = "/getCheckSalaryStdById")
    public Result<CheckSalaryStd> getCheckSalaryStdById(@RequestParam("checkid") Integer checkid) {
        try {
            CheckSalaryStd checkSalaryStd = checkSalaryStdService.getCheckSalaryStdById(checkid);
            return new Result<CheckSalaryStd>(200, "查询成功", checkSalaryStd);
        } catch (Exception e) {
            return new Result<CheckSalaryStd>(500, "查询失败", null);
        }
    }

    //复核一条薪资标准请求
    @RequiresPermissions({20}) // 需要权限ID 20
    @RequestMapping(value = "/checkSalaryStd")
    public Result<String> checkSalaryStd(HttpServletRequest request,  @RequestBody Check check) {
        Map<String, Object> userInfo = userInfoClient.getUserInfo();
        String checker = (String) userInfo.get("username");
        boolean flag = checkSalaryStdService.checkSalaryStd(check.getCheckid(), checker, check.getIsagree());
        if(flag){
            return new Result<String>(200, "复核操作成功", null);
        }else{
            return new Result<String>(500, "复核操作失败", null);
        }
    }
}
