package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/employees")
public interface EmployeeController {

    @GetMapping
    CompletableFuture<ResponseEntity<List<Employee>>> getAllEmployees() throws IOException;

    @GetMapping("/search/{searchString}")
    CompletableFuture<ResponseEntity<List<Employee>>> getEmployeesByNameSearch(@PathVariable String searchString);

    @GetMapping("/{id}")
    CompletableFuture<ResponseEntity<Employee>> getEmployeeById(@PathVariable String id);

    @GetMapping("/highestSalary")
    CompletableFuture<ResponseEntity<Integer>> getHighestSalaryOfEmployees();

    @GetMapping("/topTenHighestEarningEmployeeNames")
    CompletableFuture<ResponseEntity<List<String>>> getTopTenHighestEarningEmployeeNames();

    @PostMapping
    CompletableFuture<ResponseEntity<String>> createEmployee(@RequestBody Map<String, Object> employeeInput);

    @DeleteMapping("/{id}")
    CompletableFuture<ResponseEntity<String>> deleteEmployeeById(@PathVariable String id);
}
