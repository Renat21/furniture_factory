package com.example.factory.repository;

import com.example.factory.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findEmployeeByIsFree(boolean isFree);

    public Employee findEmployeeById(Long id);

    public void deleteEmployeeById(Long id);
}