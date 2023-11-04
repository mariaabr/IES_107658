package com.ies.multilayer.service;

import com.ies.multilayer.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long employeeId);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

    List<Employee> getByEmail(String email);
}