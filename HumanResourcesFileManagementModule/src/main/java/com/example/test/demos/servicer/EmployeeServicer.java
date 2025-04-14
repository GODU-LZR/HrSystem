package com.example.test.demos.servicer;

import com.example.test.demos.pojo.Employee;

import java.util.List;

public interface EmployeeServicer {
    String insertEmployee(Employee employee);
    String deleteEmployee(Integer archiveId);
    String updateEmployee(Employee employee);
    List<Employee> selectByConditions(Employee employee);
}
