package com.example.factory.service;


import com.example.factory.entity.*;
import com.example.factory.repository.DepartmentRepository;
import com.example.factory.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public Employee findEmployeeById(Long id){
        return employeeRepository.findEmployeeById(id);
    }

    @Transactional
    public void deleteEmployeeById(Long id){
        Employee employee = employeeRepository.findEmployeeById(id);
        Department department = employee.getDepartment();
        department.getEmployees().remove(employee);
        employeeRepository.deleteEmployeeById(id);
    }

    public Employee save(Employee employee, String address){
        employee.setIsFree(false);
        Department department = departmentRepository.findDepartmentByAddress(address);
        employee.setDepartment(department);
        employee = employeeRepository.save(employee);

        department.getEmployees().add(employee);
        departmentRepository.save(department);
        return employee;
    }

    public Employee updateEmployee(Employee newEmployee, Long rawId, String departmentName){
        Employee employee = employeeRepository.findEmployeeById(rawId);

        Department oldDepartment = employee.getDepartment();
        oldDepartment.getEmployees().remove(employee);
        departmentRepository.save(oldDepartment);


        Department newDepartment = departmentRepository.findDepartmentByAddress(departmentName);

        employee.setDepartment(newDepartment);
        employee.setSurname(newEmployee.getSurname());
        employee.setExperience(newEmployee.getExperience());
        employee.setSpeciality(newEmployee.getSpeciality());
        employee.setName(newEmployee.getName());
        Employee dbEmployee = employeeRepository.save(employee);

        newDepartment.getEmployees().add(dbEmployee);
        departmentRepository.save(newDepartment);

        return dbEmployee;
    }
}
