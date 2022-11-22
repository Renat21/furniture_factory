package com.example.factory.controller.AdminController;

import com.example.factory.Enum.Role;
import com.example.factory.entity.Raws;
import com.example.factory.entity.User;
import com.example.factory.repository.ProvidersRepository;
import com.example.factory.repository.RawsRepository;
import com.example.factory.service.RawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminRawController {
    @Autowired
    RawsRepository rawsRepository;

    @Autowired
    RawService rawService;

    @Autowired
    ProvidersRepository providersRepository;

    @GetMapping("/adminRaw")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("raw", rawsRepository.findAll());
        model.addAttribute("providers", providersRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminRaw";
    }

    @PostMapping(value = "/getRaw/{id}")
    @ResponseBody
    public Raws getRaw(@PathVariable Long id){
        return rawService.findRawById(id);
    }

    @PostMapping(value = "/addRaw/{idProvider}")
    @ResponseBody
    public Raws addRaw(@RequestBody Raws raws, @PathVariable String idProvider){
        return rawService.save(raws, providersRepository.findProvidersByName(idProvider));
    }

    @PostMapping(value = "/updateRaw/{rawId}/{departmentName}")
    @ResponseBody
    public Raws updateRaw(@RequestBody Raws newRaw, @PathVariable Long rawId, @PathVariable String departmentName){
        return rawService.updateRaw(newRaw, rawId, departmentName);
    }


    @PostMapping(value = "/deleteRaw/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        rawService.deleteRawById(id);
        return id;
    }

}
