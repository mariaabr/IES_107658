package com.ies.multilayer.controller;

import java.util.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.ies.multilayer.entity.Employee;
import com.ies.multilayer.repository.EmployeeRepository;
import com.ies.multilayer.service.EmployeeService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class EmployeeController {
    
    // @Autowired
    private EmployeeService employeeService;

    // build create Employee REST API
    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee){
        Employee savedEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // build get employee by id REST API
    // http://localhost:8080/api/employees/1
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    // Build Get All Employees REST API
    // http://localhost:8080/api/employees
    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "none") String email){

        List<Employee> employees;

        if (email.equals("none")) {
            employees = employeeService.getAllEmployees();
        }
        else {
            employees = employeeService.getByEmail(email);
        }
        
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // @GetMapping("/employees/{id}")
    // public ResponseEntity<Employee> getEmployeesByEmail(@RequestParam(required = false) String email){
    //     Employee employee = employeeService.findByEmailId(email);
    //     return  new ResponseEntity<>(employee, HttpStatus.OK);
    // }

    // Build Update Employee REST API
    @PutMapping("/employees/{id}")
    // http://localhost:8080/api/employees/1
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId,
                                           @RequestBody Employee employee){
        employee.setId(employeeId);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // Build Delete Employee REST API
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
    }
}