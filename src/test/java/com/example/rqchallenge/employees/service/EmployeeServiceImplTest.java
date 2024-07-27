package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeeResponseSingle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(List.of(new Employee()));

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CompletableFuture<ResponseEntity<List<Employee>>> future = employeeService.getAllEmployees();
        ResponseEntity<List<Employee>> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(List.of(new Employee()));

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CompletableFuture<ResponseEntity<List<Employee>>> future = employeeService.getEmployeesByNameSearch("test");
        ResponseEntity<List<Employee>> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testGetEmployeeById() {
        EmployeeResponseSingle mockResponse = new EmployeeResponseSingle();
        mockResponse.setStatus("success");
        mockResponse.setData(new Employee());

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employee/1", EmployeeResponseSingle.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CompletableFuture<ResponseEntity<Employee>> future = employeeService.getEmployeeById("1");
        ResponseEntity<Employee> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse.getData(), response.getBody());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(List.of(new Employee("1", "John Doe", "100000", "30", "")));

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CompletableFuture<ResponseEntity<Integer>> future = employeeService.getHighestSalaryOfEmployees();
        ResponseEntity<Integer> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100000, response.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        EmployeeResponse mockResponse = new EmployeeResponse();
        mockResponse.setStatus("success");
        mockResponse.setData(List.of(new Employee("1", "John Doe", "100000", "30", "")));

        when(restTemplate.getForEntity("https://dummy.restapiexample.com/api/v1/employees", EmployeeResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        CompletableFuture<ResponseEntity<List<String>>> future = employeeService.getTopTenHighestEarningEmployeeNames();
        ResponseEntity<List<String>> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0));
    }

    @Test
    void testCreateEmployee() {
        Map<String, Object> employeeInput = Map.of("name", "John Doe", "salary", "100000", "age", "30");

        Map<String, Object> mockResponseBody = Map.of("status", "success");
        ResponseEntity<Map<String, Object>> mockResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(restTemplate.exchange(
                ArgumentMatchers.eq("https://dummy.restapiexample.com/api/v1/create"),
                ArgumentMatchers.eq(HttpMethod.POST),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Object>>>any())
        ).thenReturn(mockResponse);

        CompletableFuture<ResponseEntity<String>> future = employeeService.createEmployee(employeeInput);
        ResponseEntity<String> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());
    }

    @Test
    void testDeleteEmployeeById() {
        Map<String, Object> mockResponseBody = Map.of("status", "success");
        ResponseEntity<Map<String, Object>> mockResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);

        when(restTemplate.exchange(
                ArgumentMatchers.eq("https://dummy.restapiexample.com/api/v1/delete/1"),
                ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),
                ArgumentMatchers.<ParameterizedTypeReference<Map<String, Object>>>any())
        ).thenReturn(mockResponse);

        CompletableFuture<ResponseEntity<String>> future = employeeService.deleteEmployeeById("1");
        ResponseEntity<String> response = future.join();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee with id 1 has been successfully deleted.", response.getBody());
    }
}
