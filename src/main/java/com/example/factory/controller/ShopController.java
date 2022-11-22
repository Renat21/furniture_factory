package com.example.factory.controller;


import com.example.factory.Enum.Role;
import com.example.factory.entity.Product;
import com.example.factory.entity.User;
import com.example.factory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopController {

    @Autowired
    ProductService productService;

    @GetMapping("/shop")
    public String getShop(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        model.addAttribute("allProducts", new ArrayList[0]);
        return "shop";
    }

    @RequestMapping(value = "/allProducts", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getAllProducts(){
        return productService.getProducts();
    }

    @RequestMapping(value="/addProduct/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void addProduct(@PathVariable Long id, @AuthenticationPrincipal User user){
        productService.addProductToOrder(user, id);
    }
}
