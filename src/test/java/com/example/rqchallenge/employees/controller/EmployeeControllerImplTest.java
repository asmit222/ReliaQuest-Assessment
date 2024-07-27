package com.example.rqchallenge.employees.controller;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeControllerImplTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeControllerImpl employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        List<Employee> employees = List.of(new Employee(), new Employee());
        when(employeeService.getAllEmployees()).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(employees)));

        CompletableFuture<ResponseEntity<List<Employee>>> future = employeeController.getAllEmployees();
        ResponseEntity<List<Employee>> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseEntity.getBody().size());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    public void testGetEmployeesByNameSearch() throws Exception {
        List<Employee> employees = List.of(new Employee(), new Employee());
        when(employeeService.getEmployeesByNameSearch(anyString())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(employees)));

        CompletableFuture<ResponseEntity<List<Employee>>> future = employeeController.getEmployeesByNameSearch("John");
        ResponseEntity<List<Employee>> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseEntity.getBody().size());
        verify(employeeService, times(1)).getEmployeesByNameSearch("John");
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        when(employeeService.getEmployeeById(anyString())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(employee)));

        CompletableFuture<ResponseEntity<Employee>> future = employeeController.getEmployeeById("1");
        ResponseEntity<Employee> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        verify(employeeService, times(1)).getEmployeeById("1");
    }

    @Test
    public void testGetHighestSalaryOfEmployees() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(100000)));

        CompletableFuture<ResponseEntity<Integer>> future = employeeController.getHighestSalaryOfEmployees();
        ResponseEntity<Integer> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(100000, responseEntity.getBody());
        verify(employeeService, times(1)).getHighestSalaryOfEmployees();
    }

    @Test
    public void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> employeeNames = List.of("John", "Jane");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok(employeeNames)));

        CompletableFuture<ResponseEntity<List<String>>> future = employeeController.getTopTenHighestEarningEmployeeNames();
        ResponseEntity<List<String>> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(2, responseEntity.getBody().size());
        verify(employeeService, times(1)).getTopTenHighestEarningEmployeeNames();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(Map.class))).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok("success")));

        Map<String, Object> employeeInput = Map.of("name", "John", "salary", "100000", "age", "30");
        CompletableFuture<ResponseEntity<String>> future = employeeController.createEmployee(employeeInput);
        ResponseEntity<String> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", responseEntity.getBody());
        verify(employeeService, times(1)).createEmployee(employeeInput);
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        when(employeeService.deleteEmployeeById(anyString())).thenReturn(CompletableFuture.completedFuture(ResponseEntity.ok("success")));

        CompletableFuture<ResponseEntity<String>> future = employeeController.deleteEmployeeById("1");
        ResponseEntity<String> responseEntity = future.get();

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("success", responseEntity.getBody());
        verify(employeeService, times(1)).deleteEmployeeById("1");
    }
}
