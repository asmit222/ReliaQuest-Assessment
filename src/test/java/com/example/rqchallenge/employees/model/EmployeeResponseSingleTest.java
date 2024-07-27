package com.example.rqchallenge.employees.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeResponseSingleTest {

    @Test
    void testGettersAndSetters() {
        EmployeeResponseSingle response = new EmployeeResponseSingle();
        response.setStatus("success");
        Employee employee = new Employee("1", "John Doe", "50000", "30", "image.jpg");
        response.setData(employee);

        assertEquals("success", response.getStatus());
        assertEquals("John Doe", response.getData().getEmployee_name());
    }
}
