package com.example.factory.controller.AdminController;


import com.example.factory.Enum.Role;
import com.example.factory.entity.*;
import com.example.factory.repository.*;
import com.example.factory.service.DepartmentService;
import com.example.factory.service.ImageService;
import com.example.factory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminProductController {
    @Autowired
    ImageService imageService;
    @Autowired
    ProductService productService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    RawsRepository rawsRepository;

    @Autowired
    ProvidersRepository providersRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/adminProduct")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("products", productService.getProducts());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminProduct";
    }


    @PostMapping(value = "/getProducts/{id}")
    @ResponseBody
    public Product getProducts(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping(value = "/addProducts/{id}")
    @ResponseBody
    public Product addProducts(@RequestBody Product product, @PathVariable Long id){
        product.setImage(imageService.findImageById(id));
        productService.save(product);
        return product;
    }

    @PostMapping(value = "/updateProducts/{productId}/{imageId}")
    @ResponseBody
    public Product updateProducts(@RequestBody Product newProduct, @PathVariable Long productId, @PathVariable Long imageId){
        Product product = productService.getProductById(productId);
        product.setCharacteristic(newProduct.getCharacteristic());
        product.setCost(newProduct.getCost());
        product.setDescription(newProduct.getDescription());
        product.setType(newProduct.getType());
        product.setName(newProduct.getName());
        product.setImage(imageService.findImageById(imageId));
        productService.save(product);
        return product;
    }

    @PostMapping(value = "/deleteProducts/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        productService.deleteProductById(id);
        return id;
    }
}
