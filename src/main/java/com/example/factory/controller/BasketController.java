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

import java.util.List;

@Controller
public class BasketController {

    @Autowired
    ProductService productService;

    @GetMapping("/basket")
    public String getBasket(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "basket";
    }

    @RequestMapping(value = "/allUsersProducts", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getAllUsersProducts(@AuthenticationPrincipal User user){
        return productService.getUsersProducts(user);
    }

    @RequestMapping(value="/deleteProduct/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Long deleteProduct(@PathVariable Long id, @AuthenticationPrincipal User user){
        productService.deleteProductFromOrder(user, id);
        return id;
    }

    @RequestMapping(value="/buyProducts", method = RequestMethod.POST)
    @ResponseBody
    public void buyProduct(@AuthenticationPrincipal User user){
        productService.buyProducts(user);
    }

}
