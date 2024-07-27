package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.model.Employee;
import com.example.rqchallenge.employees.model.EmployeeResponse;
import com.example.rqchallenge.employees.model.EmployeeResponseSingle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private static final String BASE_URL = "https://dummy.restapiexample.com/api/v1";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final RestTemplate restTemplate;

    public EmployeeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public CompletableFuture<ResponseEntity<List<Employee>>> getAllEmployees() {
        logger.info("Fetching all employees.");
        String url = BASE_URL + "/employees";

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(url, EmployeeResponse.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    List<Employee> employees = response.getBody().getData();
                    logger.info("Successfully fetched {} employees.", employees.size());
                    return ResponseEntity.ok(employees);
                } else {
                    logger.error("Failed to fetch employees. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while fetching employees.", e);
                return ResponseEntity.status(500).build();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<Employee>>> getEmployeesByNameSearch(String searchString) {
        logger.info("Searching employees by name: {}", searchString);
        String url = BASE_URL + "/employees";
    
        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(url, EmployeeResponse.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    List<Employee> employees = response.getBody().getData();
                    List<Employee> filteredEmployees = employees.stream()
                            .filter(employee -> employee.getEmployee_name() != null && employee.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
                            .collect(Collectors.toList());
                    logger.info("Found {} employees matching the search string '{}'.", filteredEmployees.size(), searchString);
                    return ResponseEntity.ok(filteredEmployees);
                } else {
                    logger.error("Failed to search employees. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while searching employees.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }, executorService);
    }
    

    @Override
    public CompletableFuture<ResponseEntity<Employee>> getEmployeeById(String id) {
        logger.info("Fetching employee by id: {}", id);
        String url = BASE_URL + "/employee/" + id;

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<EmployeeResponseSingle> response = restTemplate.getForEntity(url, EmployeeResponseSingle.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    Employee employee = response.getBody().getData();
                    logger.info("Successfully fetched employee with id: {}", id);
                    return ResponseEntity.ok(employee);
                } else {
                    logger.error("Failed to fetch employee. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while fetching employee by id.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<ResponseEntity<Integer>> getHighestSalaryOfEmployees() {
        logger.info("Fetching the highest salary of employees.");
        String url = BASE_URL + "/employees";

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(url, EmployeeResponse.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    List<Employee> employees = response.getBody().getData();
                    int highestSalary = employees.stream()
                            .mapToInt(e -> Integer.parseInt(e.getEmployee_salary()))
                            .max()
                            .orElse(0);
                    logger.info("The highest salary among employees is: {}", highestSalary);
                    return ResponseEntity.ok(highestSalary);
                } else {
                    logger.error("Failed to fetch highest salary. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while fetching highest salary of employees.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<String>>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Fetching top ten highest earning employee names.");
        String url = BASE_URL + "/employees";

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<EmployeeResponse> response = restTemplate.getForEntity(url, EmployeeResponse.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    List<Employee> employees = response.getBody().getData();
                    List<String> topTenEmployees = employees.stream()
                            .sorted((e1, e2) -> Integer.compare(Integer.parseInt(e2.getEmployee_salary()), Integer.parseInt(e1.getEmployee_salary())))
                            .limit(10)
                            .map(Employee::getEmployee_name)
                            .collect(Collectors.toList());
                    logger.info("Successfully fetched top ten highest earning employee names.");
                    return ResponseEntity.ok(topTenEmployees);
                } else {
                    logger.error("Failed to fetch top ten highest earning employee names. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).build();
                }
            } catch (Exception e) {
                logger.error("Error occurred while fetching top ten highest earning employee names.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<ResponseEntity<String>> createEmployee(Map<String, Object> employeeInput) {
        logger.info("Creating employee with input: {}", employeeInput);
        String url = BASE_URL + "/create";

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new org.springframework.http.HttpEntity<>(employeeInput),
                    new ParameterizedTypeReference<Map<String, Object>>() {}
                );
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && "success".equals(response.getBody().get("status"))) {
                    logger.info("Successfully created employee.");
                    return ResponseEntity.ok("success");
                } else {
                    logger.error("Failed to create employee. Status code: {}", response.getStatusCode());
                    return ResponseEntity.status(response.getStatusCode()).body("failed");
                }
            } catch (Exception e) {
                logger.error("Error occurred while creating employee.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
            }
        }, executorService);
    }

    @Override
    public CompletableFuture<ResponseEntity<String>> deleteEmployeeById(String id) {
        logger.info("Deleting employee by id: {}", id);
        String url = BASE_URL + "/delete/" + id;

        return CompletableFuture.supplyAsync(() -> {
            try {
                ResponseEntity<Map<String, Object>> deleteResponse = restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
                );
                if (deleteResponse.getStatusCode().is2xxSuccessful() && deleteResponse.getBody() != null && "success".equals(deleteResponse.getBody().get("status"))) {
                    logger.info("Successfully deleted employee with id: {}", id);
                    return ResponseEntity.ok("Employee with id " + id + " has been successfully deleted.");
                } else {
                    logger.error("Failed to delete employee. Status code: {}", deleteResponse.getStatusCode());
                    return ResponseEntity.status(deleteResponse.getStatusCode()).body("Failed to delete employee.");
                }
            } catch (Exception e) {
                logger.error("Error occurred while deleting employee by id.", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee.");
            }
        }, executorService);
    }
}
