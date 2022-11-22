package com.example.factory.service;


import com.example.factory.entity.Department;
import com.example.factory.entity.Employee;
import com.example.factory.entity.Raws;
import com.example.factory.repository.DepartmentRepository;
import com.example.factory.repository.EmployeeRepository;
import com.example.factory.repository.RawsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    public Department save(Department department){
        return departmentRepository.save(department);
    }

    @Transactional
    public void deleteDepartmentById(Long id){
        departmentRepository.deleteDepartmentById(id);
    }

    public Department findDepartmentById(Long id){
        return departmentRepository.findDepartmentById(id);
    }
}
