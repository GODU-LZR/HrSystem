package com.example.test.demos.controller;

import com.example.test.demos.annotations.RequiresPermissions;
import com.example.test.demos.feign.SalaryStandClient;
import com.example.test.demos.pojo.Employee;
import com.example.test.demos.servicer.EmployeeServicer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServicer employeeServicer;

    @Autowired
    private SalaryStandClient salaryStandClient;

    // 定义图片保存的路径，可以根据需要调整
    private static final String IMAGE_UPLOAD_DIR = "uploads/privateImage";

    @RequiresPermissions({11}) // 需要权限ID 11
    @ApiOperation(value = "人力资源档案登记")
    @PostMapping
    public String insertEmployee(@RequestBody Employee employee) {
        if (employee.getEmployeePhotoUrl() != null && !employee.getEmployeePhotoUrl().isEmpty()) {
            String photoUrl = saveEmployeePhoto(employee.getEmployeePhotoUrl());
            employee.setEmployeePhotoUrl(photoUrl);
        }

        if (employee == null) {
            return "{\"status\": \"登记失败\"}";
        } else {
            employee.setIsdeleted(0);
            employee.setReviewId(0);
            // 调用服务层处理数据插入
            String result = employeeServicer.insertEmployee(employee);
            // 返回 JSON 响应，动态设置状态
            return "{\"status\": \"" + result + "\"}";
        }
    }

    @RequiresPermissions({15}) // 需要权限ID 15
    @ApiOperation(value = "人力资源档案删除")
    @PutMapping("/delete")
    public String deleteEmployee(@RequestBody Employee employee) {
        Employee employeeConditions = new Employee();
        employeeConditions.setArchiveId(employee.getArchiveId());
        List<Employee> employees = employeeServicer.selectByConditions(employeeConditions);
        Employee employeeDateBase = employees.get(0);
        employee.setReviewId(employeeDateBase.getReviewId());
        employee.setIsdeleted(1);
        String result = employeeServicer.updateEmployee(employee);
        // 返回 JSON 响应，动态设置状态
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({12}) // 需要权限ID 12
    @ApiOperation("人力资源档案变更")
    @PutMapping("/update")
    public String updateEmployee(@RequestBody Employee employee) {
        // 判断是否是 Base64 编码的照片（即新上传的照片）
        if (employee.getEmployeePhotoUrl() != null && !employee.getEmployeePhotoUrl().isEmpty()) {
            if (employee.getEmployeePhotoUrl().startsWith("data:image")) {
                // 如果是 Base64 编码，说明是新照片，保存并更新
                String photoUrl = saveEmployeePhoto(employee.getEmployeePhotoUrl());
                employee.setEmployeePhotoUrl(photoUrl);
            }
        }
        // 更新员工数据
        Employee employeeConditions = new Employee();
        employeeConditions.setArchiveId(employee.getArchiveId());
        List<Employee> employees = employeeServicer.selectByConditions(employeeConditions);
        Employee employeeDateBase = employees.get(0);
        employee.setReviewId(employeeDateBase.getReviewId());
        employee.setIsdeleted(employeeDateBase.getIsdeleted());

        String result = employeeServicer.updateEmployee(employee);
        // 返回 JSON 响应，动态设置状态
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({13}) // 需要权限ID 13
    @ApiOperation("人力资源档案登记复核")
    @PutMapping("/review")
    public String reviewEmployee(@RequestBody Employee employee) {
        // 判断是否是 Base64 编码的照片（即新上传的照片）
        if (employee.getEmployeePhotoUrl() != null && !employee.getEmployeePhotoUrl().isEmpty()) {
            if (employee.getEmployeePhotoUrl().startsWith("data:image")) {
                // 如果是 Base64 编码，说明是新照片，保存并更新
                String photoUrl = saveEmployeePhoto(employee.getEmployeePhotoUrl());
                employee.setEmployeePhotoUrl(photoUrl);
            }
        }
        // 更新员工数据
        Employee employeeConditions = new Employee();
        employeeConditions.setArchiveId(employee.getArchiveId());
        List<Employee> employees = employeeServicer.selectByConditions(employeeConditions);
        Employee employeeDateBase = employees.get(0);
        employee.setIsdeleted(employeeDateBase.getIsdeleted());
        employee.setReviewId(1);
        String result = employeeServicer.updateEmployee(employee);
        return "{\"status\": \"" + result + "\"}";
    }

    @RequiresPermissions({14}) // 需要权限ID 14
    @ApiOperation("人力资源档案查询")
    @PutMapping("/query")
    public List<Employee> getAllEmployees(@RequestBody Employee employee) {
        List<Employee> employees = employeeServicer.selectByConditions(employee);
        return employees;
    }

    // 保存员工照片并返回图片的 URL 路径
    private String saveEmployeePhoto(String photoBase64) {
        try {
            // 移除Data URL前缀（如"data:image/png;base64,"）
            String base64Data = photoBase64.substring(photoBase64.indexOf(",") + 1);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            String fileName = UUID.randomUUID().toString() + ".png"; // 生成唯一的文件名
            File targetFile = new File(IMAGE_UPLOAD_DIR, fileName);
            targetFile.getParentFile().mkdirs();  // 创建目标文件夹
            Files.write(targetFile.toPath(), decodedBytes);  // 将图片保存到文件中

            // 返回图片相对路径
            return "/privateImage/" + fileName;  // 返回相对路径，前端可以直接访问
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Please try again!", e);
        }
    }

    /**
     * 获取薪资标准列表
     * @return Map<Integer, String> 薪资标准的ID和名称
     */
    @GetMapping("/salary-standards")
    public ResponseEntity<?> getSalaryStandards() {
        try {
            // 调用 Feign 客户端获取薪资标准列表
            Map<Integer, String> salaryStandards = salaryStandClient.getSidAndSname();

            // 返回薪资标准列表给前端
            return ResponseEntity.ok(salaryStandards);
        } catch (Exception e) {
            // 如果出现错误，返回500状态码和错误信息
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("获取薪资标准失败，请稍后再试");
        }
    }
}
