package com.Employe.Main.service;

import com.Employe.Main.entity.Employee;
import com.Employe.Main.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    // Create a new employee
    public Employee createEmployee(Employee employee) {
        // Check if email already exists
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new RuntimeException("Employee with email " + employee.getEmail() + " already exists");
        }
        return employeeRepository.save(employee);
    }
    
    // Update an existing employee
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            
            // Check if email is being changed and if the new email already exists
            if (!employee.getEmail().equals(employeeDetails.getEmail()) && 
                employeeRepository.existsByEmail(employeeDetails.getEmail())) {
                throw new RuntimeException("Employee with email " + employeeDetails.getEmail() + " already exists");
            }
            
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setDepartment(employeeDetails.getDepartment());
            employee.setSalary(employeeDetails.getSalary());
            
            return employeeRepository.save(employee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
    
    // Delete employee by ID
    public void deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }
    
    // Get employee by email
    public Optional<Employee> getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    
    // Get employees by department
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }
    
    // Get employees by name (first or last name containing the search term)
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByNameContaining(name);
    }
    
    // Get employees by salary range
    public List<Employee> getEmployeesBySalaryRange(Double minSalary, Double maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }
    
    // Get employees by department with salary greater than specified amount
    public List<Employee> getEmployeesByDepartmentAndSalary(String department, Double salary) {
        return employeeRepository.findByDepartmentAndSalaryGreaterThan(department, salary);
    }
    
    // Check if employee exists by email
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }
    
    // Get total number of employees
    public long getTotalEmployeeCount() {
        return employeeRepository.count();
    }
}