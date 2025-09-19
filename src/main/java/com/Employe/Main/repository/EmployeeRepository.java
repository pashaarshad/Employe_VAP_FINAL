package com.Employe.Main.repository;

import com.Employe.Main.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Find employee by email
    Optional<Employee> findByEmail(String email);
    
    // Find employees by department
    List<Employee> findByDepartment(String department);
    
    // Find employees by first name (case insensitive)
    List<Employee> findByFirstNameIgnoreCase(String firstName);
    
    // Find employees by last name (case insensitive)
    List<Employee> findByLastNameIgnoreCase(String lastName);
    
    // Find employees by salary range
    List<Employee> findBySalaryBetween(Double minSalary, Double maxSalary);
    
    // Custom query to find employees by department and salary greater than
    @Query("SELECT e FROM Employee e WHERE e.department = :department AND e.salary > :salary")
    List<Employee> findByDepartmentAndSalaryGreaterThan(@Param("department") String department, 
                                                       @Param("salary") Double salary);
    
    // Custom query to find employees by name containing (search functionality)
    @Query("SELECT e FROM Employee e WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> findByNameContaining(@Param("name") String name);
    
    // Check if email exists
    boolean existsByEmail(String email);
}