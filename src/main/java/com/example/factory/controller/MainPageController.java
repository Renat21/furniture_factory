package com.example.factory.controller;


import com.example.factory.Enum.Role;
import com.example.factory.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "index";
    }

}
