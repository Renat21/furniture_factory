package com.example.factory.controller.AdminController;


import com.example.factory.Enum.Role;
import com.example.factory.entity.Department;
import com.example.factory.entity.User;
import com.example.factory.repository.DepartmentRepository;
import com.example.factory.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminDepartmentController {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/adminDepartment")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("department", departmentRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminDepartment";
    }


    @PostMapping(value = "/getDepartment/{id}")
    @ResponseBody
    public Department getDepartment(@PathVariable Long id){
        return departmentRepository.findDepartmentById(id);
    }

    @PostMapping(value = "/addDepartment")
    @ResponseBody
    public Department addProducts(@RequestBody Department department){
        return departmentService.save(department);
    }

    @PostMapping(value = "/updateDepartment/{departmentId}")
    @ResponseBody
    public Department updateProducts(@RequestBody Department newDepartment, @PathVariable Long departmentId){
        Department department = departmentService.findDepartmentById(departmentId);
        department.setAddress(newDepartment.getAddress());
        department.setSpecialization(newDepartment.getSpecialization());
        department.setTelephoneNumber(newDepartment.getTelephoneNumber());
        departmentService.save(department);
        return department;
    }

    @PostMapping(value = "/deleteDepartment/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        departmentService.deleteDepartmentById(id);
        return id;
    }

}
