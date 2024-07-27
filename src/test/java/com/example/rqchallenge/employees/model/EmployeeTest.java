package com.example.rqchallenge.employees.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testGettersAndSetters() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployee_name("John Doe");
        employee.setEmployee_salary("50000");
        employee.setEmployee_age("30");
        employee.setProfile_image("image.jpg");

        assertEquals("1", employee.getId());
        assertEquals("John Doe", employee.getEmployee_name());
        assertEquals("50000", employee.getEmployee_salary());
        assertEquals("30", employee.getEmployee_age());
        assertEquals("image.jpg", employee.getProfile_image());
    }

    @Test
    void testConstructorWithParameters() {
        Employee employee = new Employee("1", "John Doe", "50000", "30", "image.jpg");

        assertEquals("1", employee.getId());
        assertEquals("John Doe", employee.getEmployee_name());
        assertEquals("50000", employee.getEmployee_salary());
        assertEquals("30", employee.getEmployee_age());
        assertEquals("image.jpg", employee.getProfile_image());
    }

    @Test
    void testEmptyConstructor() {
        Employee employee = new Employee();

        assertNull(employee.getId());
        assertNull(employee.getEmployee_name());
        assertNull(employee.getEmployee_salary());
        assertNull(employee.getEmployee_age());
        assertNull(employee.getProfile_image());
    }
}
