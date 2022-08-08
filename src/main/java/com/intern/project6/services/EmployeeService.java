package com.intern.project6.services;

import com.intern.project6.exceptions.EmployeeNotFoundException;
import com.intern.project6.models.Employee;
import com.intern.project6.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmployee(String id) {
        Optional<Employee> byId = employeeRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        else throw new EmployeeNotFoundException("Employee with id:" + id + " was not found.");

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void updateEmployee(Employee employee) {
        if(employeeRepository.existsById(employee.getId())){
            employeeRepository.save(employee);
        }
        else throw new EmployeeNotFoundException("Employee with id:" + employee.getId() + " was not found.");
    }

    public void deleteEmployee(String id) {
        if(employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
        else throw new EmployeeNotFoundException("Employee with id:" + id + " was not found.");
    }
}
