package com.example.factory.controller.AdminController;


import com.example.factory.Enum.Role;
import com.example.factory.entity.*;
import com.example.factory.repository.*;
import com.example.factory.service.DepartmentService;
import com.example.factory.service.EmployeeService;
import com.example.factory.service.ImageService;
import com.example.factory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminEmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping("/adminEmployee")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("employee", employeeRepository.findAll());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminEmployee";
    }


    @PostMapping(value = "/getEmployee/{id}")
    @ResponseBody
    public Employee getEmployee(@PathVariable Long id){
        return employeeRepository.findEmployeeById(id);
    }

    @PostMapping(value = "/addEmployee/{departmentId}")
    @ResponseBody
    public Employee addProducts(@RequestBody Employee employee, @PathVariable String departmentId){
        return employeeService.save(employee, departmentId);
    }

    @PostMapping(value = "/updateEmployee/{employeeId}/{departmentAddress}")
    @ResponseBody
    public Employee updateProducts(@RequestBody Employee newEmployee, @PathVariable Long employeeId, @PathVariable String departmentAddress){
        return employeeService.updateEmployee(newEmployee, employeeId, departmentAddress);
    }

    @PostMapping(value = "/deleteEmployee/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return id;
    }

}
