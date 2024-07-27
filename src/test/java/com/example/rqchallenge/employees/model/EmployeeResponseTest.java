package com.example.rqchallenge.employees.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeResponseTest {

    @Test
    void testGettersAndSetters() {
        EmployeeResponse response = new EmployeeResponse();
        response.setStatus("success");
        List<Employee> employees = List.of(new Employee("1", "John Doe", "50000", "30", "image.jpg"));
        response.setData(employees);

        assertEquals("success", response.getStatus());
        assertEquals(1, response.getData().size());
        assertEquals("John Doe", response.getData().get(0).getEmployee_name());
    }
}
