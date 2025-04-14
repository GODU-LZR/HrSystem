package com.example.test.demos.servicer.imp;

import com.example.test.demos.mappers.EmployeeMapper;
import com.example.test.demos.mappers.OrganizationMapper;
import com.example.test.demos.pojo.Employee;
import com.example.test.demos.pojo.Organization;
import com.example.test.demos.servicer.EmployeeServicer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class EmployeeServicerImp implements EmployeeServicer {

    @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Autowired(required = false)
    private OrganizationMapper organizationMapper;

    @Override
    public String insertEmployee(Employee employee) {
        // 1. 生成档案编号
        Integer archiveId = generateArchiveId(employee);
        employee.setArchiveId(archiveId);

        // 2. 设置档案删除状态为 0 (未删除)
        employee.setIsdeleted(0);

        // 3. 设置复核状态为 0 (待复核)
        employee.setReviewId(0);

        // 4. 设置建档时间为当前时间
        employee.setRegisterTime(java.time.LocalDateTime.now());

        // 5. 插入员工记录
        employeeMapper.insert(employee);
        return "登记成功";
    }


    @Override
    public String deleteEmployee(Integer archiveId) {
        employeeMapper.delete(archiveId);
        return "删除成功";
    }

    @Override
    public String updateEmployee(Employee employee) {

        // 从网络层传来的employee对象中获取 reviewId
        Integer reviewId = employee.getReviewId();
        Integer isdeleted = employee.getIsdeleted();

        // 1. 如果 reviewId 为 0
        if (reviewId == 0 && isdeleted == 0) {
            // 设置 reviewId 为 0, isdeleted 为 0，防止数据在传输过程中被修改
            employee.setReviewId(0);
            employee.setIsdeleted(0);
            // 调用 Mapper 层的更新方法
            employeeMapper.update(employee);
            return "变更成功";
        }
        // 2. 如果 reviewId 为 1 且 isdeleted 为 0
        else if (reviewId == 1 && isdeleted == 0) {
            // 设置 reviewId 为 1, isdeleted 为 0
            employee.setReviewId(1);
            employee.setIsdeleted(0);
            // 调用 Mapper 层的更新方法
            employeeMapper.update(employee);
            return "复核成功";
        }
        else if (reviewId == 0 && isdeleted == 1){
            return "状态为待复核的员工档案不能删除";
        }
        // 3. 如果 reviewId 为 1 且 isdeleted 为 1
        else if (reviewId == 1 && isdeleted == 1) {
            // 设置 reviewId 为 1, isdeleted 为 1
            employee.setReviewId(1);
            employee.setIsdeleted(1);
            // 调用 Mapper 层的更新方法
            employeeMapper.update(employee);
            return "删除成功";
        }
            return "操作失败";
    }

    @Override
    public List<Employee> selectByConditions(Employee employee) {
        System.out.println(employee);
        return employeeMapper.selectByConditions(employee);
    }
    /**
     * 自动生成档案编号的规则：
     * 年份（4位）+ 一级结构编号（2位）+ 二级机构编号（2位）+ 三级机构编号（2位）+ 编号（2位）
     * 返回 Integer 类型
     */
    private Integer generateArchiveId(Employee employee) {
        // 获取年份的后两位
        String year = String.valueOf(java.time.LocalDate.now().getYear()).substring(3);

        // 获取一级、二级、三级机构的编号
        String orgLevel1Code = getOrgCodeByName(employee.getOrgLevel1());
        String orgLevel2Code = getOrgCodeByName(employee.getOrgLevel2());
        String orgLevel3Code = getOrgCodeByName(employee.getOrgLevel3());

        // 确保每个结构编号是两位数（不足两位前面补0）
        orgLevel1Code = String.format("%02d", Integer.parseInt(orgLevel1Code));
        orgLevel2Code = String.format("%02d", Integer.parseInt(orgLevel2Code));
        orgLevel3Code = String.format("%02d", Integer.parseInt(orgLevel3Code));

        // 获取当前已存在的档案编号的最大编号后两位
        String lastTwoDigits = getNextAvailableArchiveNumber(year, orgLevel1Code, orgLevel2Code, orgLevel3Code);

        // 拼接档案编号为字符串
        String archiveIdStr = year + orgLevel1Code + orgLevel2Code + orgLevel3Code + lastTwoDigits;

        // 转换为 Integer 类型并返回
        return Integer.parseInt(archiveIdStr);
    }

    /**
     * 根据组织名称查询组织ID（用于生成编号）
     */
    private String getOrgCodeByName(String orgName) {
        // 查询组织ID
        Organization org = organizationMapper.selectAll()
                .stream()
                .filter(o -> o.getOrgName().equals(orgName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到对应的组织名称：" + orgName));
        return String.valueOf(org.getOrgId());
    }

    /**
     * 获取当前年份、一级、二级、三级机构下的下一个编号（2位数，不重复）
     */
    private String getNextAvailableArchiveNumber(String year, String orgLevel1Code, String orgLevel2Code, String orgLevel3Code) {
        // 获取当前已存在的档案编号列表
        List<Employee> existingEmployees = employeeMapper.select();
        String prefix = year + orgLevel1Code + orgLevel2Code + orgLevel3Code;

        // 查找以当前年份+机构代码开头的编号
        List<String> existingIds = existingEmployees.stream()
                .map(Employee::getArchiveId)
                .filter(id -> id != null)
                .map(String::valueOf) // 转换为字符串
                .filter(id -> id.startsWith(prefix))
                .collect(Collectors.toList());

        // 生成不重复的编号（从01开始，确保两位数）
        int nextNumber = 1;
        while (true) {
            String candidate = String.format("%02d", nextNumber);
            String newId = prefix + candidate;
            if (!existingIds.contains(newId)) {
                return candidate; // 返回不重复的两位编号
            }
            nextNumber++;
            if (nextNumber > 99) {
                throw new RuntimeException("编号已用完，无法生成新的档案编号");
            }
        }
    }
}
