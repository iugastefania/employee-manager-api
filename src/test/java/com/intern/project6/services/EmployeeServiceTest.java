package com.intern.project6.services;

import com.intern.project6.Project6Application;
import com.intern.project6.exceptions.EmployeeNotFoundException;
import com.intern.project6.models.Employee;
import com.intern.project6.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);

    EmployeeService employeeService = new EmployeeService(employeeRepository);

    @Test
    public void testGetAllEmployees(){
        List<Employee> expectedList = Arrays.asList(new Employee("1","test",9), new Employee("2","ion", 11));
        when(employeeRepository.findAll()).thenReturn(expectedList);
        List<Employee> resultList = employeeService.getAllEmployees();
        assertEquals(expectedList, resultList);
    }

    @Test
    public void testGetEmployeeHappyPath(){
        Optional<Employee> expectedEmployee = Optional.of(new Employee("2", "ana", 15));
        when(employeeRepository.findById(anyString())).thenReturn(expectedEmployee);
        Employee resultEmployee = employeeService.getEmployee("2");
        assertEquals(expectedEmployee.get(), resultEmployee);
    }

    @Test
    public void testGetEmployee_EmployeeNotFound(){
        Optional<Employee> expectedEmployee = Optional.empty();
        when(employeeRepository.findById(anyString())).thenReturn(expectedEmployee);

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.getEmployee("2");
        });

        String expectedMessage = "Employee with id:2 was not found.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }

    @Test
    public void addEmployeeTest(){
        Employee expectedEmployee = new Employee("3", "maria", 18);
        employeeService.addEmployee(expectedEmployee);
        verify(employeeRepository, times(1)).save(expectedEmployee);
    }

    @Test
    public void updateEmployeeTestHappyPath(){
        Employee expectedEmployee = new Employee("1", "ion", 9);
        when(employeeRepository.existsById(anyString())).thenReturn(Boolean.TRUE);
        employeeService.updateEmployee(expectedEmployee);
        verify(employeeRepository, times(1)).save(expectedEmployee);
    }

    @Test
    public void testUpdateEmployee_EmployeeNotFound(){
        when(employeeRepository.existsById(anyString())).thenReturn(Boolean.FALSE);

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.updateEmployee(new Employee("2","ion", 11));
        });

        String expectedMessage = "Employee with id:2 was not found.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }


    @Test
    public void deleteEmployeeTest(){
        when(employeeRepository.existsById(anyString())).thenReturn(Boolean.TRUE);
        employeeService.deleteEmployee("1");
        verify(employeeRepository, times(1)).deleteById(anyString());
    }

    @Test
    public void deleteEmployeeTest_EmployeeNotFound(){
        when(employeeRepository.existsById(anyString())).thenReturn(Boolean.FALSE);

        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.deleteEmployee("1");
        });

        String expectedMessage = "Employee with id:1 was not found.";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage,expectedMessage);
    }
}
