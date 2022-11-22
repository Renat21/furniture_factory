package com.example.factory.repository;

import com.example.factory.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    public Department findDepartmentById(Long id);

    public void deleteDepartmentById(Long id);

    public Department findDepartmentByAddress(String address);
}