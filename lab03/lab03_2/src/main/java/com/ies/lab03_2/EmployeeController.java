package com.ies.lab03_2;

import java.util.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ies.lab03_2.Employee;
import com.ies.lab03_2.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees") //list all employees
    public List<Employee> showEmployeesList(@RequestParam(required = false) String email) {
        if(email != null) {
            return employeeRepository.findByEmailId(email);
        }
        return (List<Employee>) employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}") //search an employee by email
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId){
        for(Employee employee : employeeRepository.findAll()){
            if(employee.getId()== employeeId){
                return ResponseEntity.ok().body(employee);
            }
        }

        System.out.println("Employee not found for this id :: " + employeeId);
        return null;
    }

    @PostMapping("/employees") //add an employee
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        // if (result.hasErrors()) {
        //     return "add-employee";
        // }
        
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee){ //update an employee
        employee.setId(employeeId);
        Employee existingEmployee = employeeRepository.findById(employee.getId()).get();
        existingEmployee.setName(employee.getName());
        existingEmployee.setEmail(employee.getEmail());
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        // return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employees/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable("id") Long employeeId){ //delete an employee
        employeeRepository.deleteById(employeeId);
        // return new ResponseEntity<>("Employee successfully deleted!", HttpStatus.OK);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
