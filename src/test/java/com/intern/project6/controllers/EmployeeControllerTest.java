package com.intern.project6.controllers;

import com.intern.project6.models.Employee;
import com.intern.project6.repositories.EmployeeRepository;
import com.intern.project6.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetAllEmployeesEndpoint() throws Exception {
        List<Employee> employees = Arrays.asList(new Employee("2","ion",22), new Employee("1","maria", 11));
        when(employeeService.getAllEmployees()).thenReturn(employees);
        mockMvc.perform(get("/getAllEmployees")).andExpect(status().isOk()).andExpect(content().string(containsString("[{\"id\":\"2\",\"name\":\"ion\",\"salary\":22},{\"id\":\"1\",\"name\":\"maria\",\"salary\":11}]")));
    }

    @Test
    public void testGetEmployeeEndpoint() throws Exception{
        Employee employee = new Employee("1","ion", 0);
        when(employeeService.getEmployee(anyString())).thenReturn(employee);
        mockMvc.perform(get("/getEmployee/" + employee.getId())).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":\"1\",\"name\":\"ion\",\"salary\":0}")));
    }


    @Test
    public void testAddEmployeeEndpoint() throws Exception{
        Employee employee = new Employee("2","ion", 11);
        mockMvc.perform(post("/addEmployee").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "  \"id\": \"2\",\n" +
                "  \"name\": \"ion\",\n" +
                "  \"salary\": 11\n" +
                "}")).andExpect(status().isOk());
        verify(employeeService, times(1)).addEmployee(employee);
    }

    @Test
    public void testUpdateEmployeeEndpoint() throws Exception{
        Employee employee = new Employee("2","ion", 11);
        mockMvc.perform(put("/updateEmployee").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "  \"id\": \"2\",\n" +
                "  \"name\": \"ion\",\n" +
                "  \"salary\": 11\n" +
                "}")).andExpect(status().isOk());
        verify(employeeService, times(1)).updateEmployee(employee);
    }

    @Test
    public void testDeleteEmployeeEndpoint() throws Exception{
        Employee employee = new Employee("1","ion", 0);
        mockMvc.perform(delete("/deleteEmployee/" + employee.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(employeeService, times(1)).deleteEmployee(employee.getId());
    }

}
