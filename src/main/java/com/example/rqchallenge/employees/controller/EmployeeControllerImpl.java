package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/employees")
public class EmployeeControllerImpl implements EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerImpl.class);
    private final EmployeeService employeeService;

    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    @GetMapping
    public CompletableFuture<ResponseEntity<List<Employee>>> getAllEmployees() throws IOException {
        logger.info("Fetching all employees.");
        return employeeService.getAllEmployees();
    }

    @Override
    @GetMapping("/search/{searchString}")
    public CompletableFuture<ResponseEntity<List<Employee>>> getEmployeesByNameSearch(@PathVariable String searchString) {
        logger.info("Searching employees by name: {}", searchString);
        return employeeService.getEmployeesByNameSearch(searchString);
    }

    @Override
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Employee>> getEmployeeById(@PathVariable String id) {
        logger.info("Fetching employee by id: {}", id);
        return employeeService.getEmployeeById(id);
    }

    @Override
    @GetMapping("/highestSalary")
    public CompletableFuture<ResponseEntity<Integer>> getHighestSalaryOfEmployees() {
        logger.info("Fetching the highest salary of employees.");
        return employeeService.getHighestSalaryOfEmployees();
    }

    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public CompletableFuture<ResponseEntity<List<String>>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Fetching top ten highest earning employee names.");
        return employeeService.getTopTenHighestEarningEmployeeNames();
    }

    @Override
    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createEmployee(@RequestBody Map<String, Object> employeeInput) {
        logger.info("Creating employee with input: {}", employeeInput);
        return employeeService.createEmployee(employeeInput);
    }

    @Override
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteEmployeeById(@PathVariable String id) {
        logger.info("Deleting employee by id: {}", id);
        return employeeService.deleteEmployeeById(id);
    }
}
