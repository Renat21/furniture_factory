package com.example.factory.controller.AdminController;

import com.example.factory.Enum.Role;
import com.example.factory.entity.Providers;
import com.example.factory.entity.User;
import com.example.factory.repository.ProvidersRepository;
import com.example.factory.repository.UserRepository;
import com.example.factory.service.ProductService;
import com.example.factory.service.ProviderService;
import com.example.factory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminUserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ProductService productService;

    @GetMapping("/adminUser")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminUser";
    }

    @PostMapping(value = "/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id){
        return userRepository.findUserById(id);
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public User addUser(@RequestBody User user){
        return userService.saveUserAndReturn(user);
    }

    @PostMapping(value = "/updateUser/{userId}")
    @ResponseBody
    public User updateUser(@RequestBody User user, @PathVariable Long userId){
        return userService.updateUser(user, userId);
    }

    @PostMapping(value = "/deleteUser/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        userService.deleteUser(id);
        return id;
    }

}
