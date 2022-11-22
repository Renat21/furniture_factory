package com.example.factory.controller.AdminController;

import com.example.factory.Enum.Role;
import com.example.factory.entity.Providers;
import com.example.factory.entity.User;
import com.example.factory.repository.ProvidersRepository;
import com.example.factory.service.ProviderService;
import com.example.factory.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminProviderController {
    @Autowired
    ProvidersRepository providersRepository;

    @Autowired
    ProviderService providerService;

    @GetMapping("/adminProvider")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("provider", providersRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminProvider";
    }


    @PostMapping(value = "/getProvider/{id}")
    @ResponseBody
    public Providers getProvider(@PathVariable Long id){
        return providerService.findProviderById(id);
    }

    @PostMapping(value = "/addProvider")
    @ResponseBody
    public Providers addProvider(@RequestBody Providers providers){
        return providerService.save(providers);
    }

    @PostMapping(value = "/updateProvider/{providerId}")
    @ResponseBody
    public Providers updateProvider(@RequestBody Providers newProvider, @PathVariable Long providerId){
        Providers providers = providerService.findProviderById(providerId);
        providers.setName(newProvider.getName());
        providers.setTelephoneNumber(newProvider.getTelephoneNumber());
        providerService.save(providers);
        return providers;
    }

    @PostMapping(value = "/deleteProvider/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        providerService.deleteProviderById(id);
        return id;
    }

}
