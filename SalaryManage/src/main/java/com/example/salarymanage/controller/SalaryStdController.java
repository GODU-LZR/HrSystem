package com.example.salarymanage.controller;

import com.example.salarymanage.annotations.RequiresPermissions;
import com.example.salarymanage.feign.UserInfoClient;
import com.example.salarymanage.parameter.IdAndRegister;
import com.example.salarymanage.parameter.RemoveSalaryStd;
import com.example.salarymanage.parameter.SelectSalaryStd;
import com.example.salarymanage.pojo.CheckSalaryStd;
import com.example.salarymanage.pojo.*;
import com.example.salarymanage.result.Result;
import com.example.salarymanage.service.service.SalaryStdService;
import com.example.salarymanage.utils.Untils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/SalaryStdController")
@ResponseBody
public class SalaryStdController {

    @Autowired
    public SalaryStdService salaryStdService;

    @Autowired
    private UserInfoClient userInfoClient;

    //创建并获取序号和登记人
    @RequiresPermissions({16}) // 需要权限ID 16
    @RequestMapping(value = "/getIdAndRegister")
    public Result<IdAndRegister> getIdAndRegister(HttpServletRequest request){
        HttpSession session = request.getSession();
        try{
            Integer sid = (Integer) session.getAttribute("sid");
            Map<String, Object> userInfo = userInfoClient.getUserInfo();
            String register = (String) userInfo.get("username");

            if(sid != null) {
                return new Result<IdAndRegister>(200, "获取已创建的薪资标准", new IdAndRegister(sid, register));
            }else{
                sid = salaryStdService.getId();
                session.setAttribute("sid", sid);
                return new Result<IdAndRegister>(200, "预创建薪资标准成功", new IdAndRegister(sid, register));
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<IdAndRegister>(500, "预创建薪资标准失败", null);
        }
    }

    //获取当前登记人
    @RequiresPermissions({16}) // 需要权限ID 16
    @RequestMapping(value = "/getRegister")
    public Result<String> getRegister(HttpServletRequest request){
        HttpSession session = request.getSession();
        try{
            Map<String, Object> userInfo = userInfoClient.getUserInfo();
            String register = (String) userInfo.get("username");
            return new Result<String>(200, "获取当前登记人成功", register);
        }catch (Exception e){
            e.printStackTrace();
            return new Result<String>(500, "获取当前登记人失败", null);
        }
    }

    //新增薪资标准
    @RequiresPermissions({16}) // 需要权限ID 16
    @RequestMapping(value = "/addSalaryStd")
    public Result<String> addSalaryStd(HttpServletRequest request, @RequestBody CheckSalaryStd checkSalaryStd){
        HttpSession session = request.getSession();
        try{
            Integer sid = (Integer) session.getAttribute("sid");
            if(sid == null){
                throw new Exception();
            }
            Map<String, Object> userInfo = userInfoClient.getUserInfo();
            String register = (String) userInfo.get("username");
            checkSalaryStd.setSid(sid);
            checkSalaryStd.setRegister(register);
            boolean flag = salaryStdService.addSalaryStd(checkSalaryStd);
            if(flag){
                session.removeAttribute("sid");
                return new Result<String>(200, "添加成功", null);
            }else{
                return new Result<String>(500, "添加失败", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<String>(500,"添加失败", null);
        }
    }

    //修改薪资标准
    @RequiresPermissions({16}) // 需要权限ID 16
    @RequestMapping(value = "/modifySalaryStd")
    public Result<String> modifySalaryStd(HttpServletRequest request, @RequestBody CheckSalaryStd checkSalaryStd){
        HttpSession session = request.getSession();
        try{
            Map<String, Object> userInfo = userInfoClient.getUserInfo();
            String register = (String) userInfo.get("username");
            checkSalaryStd.setRegister(register);
            boolean flag = salaryStdService.modifySalaryStd(checkSalaryStd);
            if(flag){
                return new Result<String>(Result.Result_code_ok, "修改成功", null);
            }else{
                return new Result<String>(Result.Result_code_error, "修改失败", null);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result<String>(Result.Result_code_error, "修改失败", null);
        }
    }

    //删除薪资标准
    @RequiresPermissions({16}) // 需要权限ID 16
    @RequestMapping(value = "/removeSalaryStd")
    public Result<String> removeSalaryStd(@RequestBody RemoveSalaryStd removeSalaryStd){
        boolean flag = salaryStdService.removeSalaryStd(removeSalaryStd);
        if(flag){
            return new Result<String>(Result.Result_code_ok, "删除成功", null);
        }else{
            return new Result<String>(Result.Result_code_error, "删除成功", null);
        }
    }

    //根据数据信息获取薪资标准
    @RequestMapping(value = "/getSalaryStd")
    public Result<List<SalaryStd>> getSalaryStd(@RequestBody SelectSalaryStd selectSalaryStd){
        Untils.processTimeFields(selectSalaryStd);
        try{
            List<SalaryStd> salaryStds = salaryStdService.getSalaryStd(selectSalaryStd);
            return new Result<List<SalaryStd>>(200, "获取成功", salaryStds);
        }catch (Exception e){
            return new Result<List<SalaryStd>>(500, "获取失败", null);
        }
    }
}
