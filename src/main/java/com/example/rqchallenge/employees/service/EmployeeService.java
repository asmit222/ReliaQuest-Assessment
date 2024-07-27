package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface EmployeeService {

    CompletableFuture<ResponseEntity<List<Employee>>> getAllEmployees();

    CompletableFuture<ResponseEntity<List<Employee>>> getEmployeesByNameSearch(String searchString);

    CompletableFuture<ResponseEntity<Employee>> getEmployeeById(String id);

    CompletableFuture<ResponseEntity<Integer>> getHighestSalaryOfEmployees();

    CompletableFuture<ResponseEntity<List<String>>> getTopTenHighestEarningEmployeeNames();

    CompletableFuture<ResponseEntity<String>> createEmployee(Map<String, Object> employeeInput);

    CompletableFuture<ResponseEntity<String>> deleteEmployeeById(String id);
}
