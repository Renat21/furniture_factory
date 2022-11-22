package com.example.factory.controller.AdminController;


import com.example.factory.Enum.Role;
import com.example.factory.entity.Orders;
import com.example.factory.entity.User;
import com.example.factory.repository.OrderRepository;
import com.example.factory.service.ProductService;
import com.example.factory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AdminOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/adminOrder")
    public String getAdmin(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("user", user);
        model.addAttribute("ADMIN", Role.ROLE_ADMIN);
        return "adminOrder";
    }

    @PostMapping(value = "/getOrder/{id}")
    @ResponseBody
    public Orders getOrder(@PathVariable Long id){
        return orderRepository.findOrdersById(id);
    }

    @PostMapping(value = "/addOrder")
    @ResponseBody
    public Orders addOrder(@RequestBody Orders orders){
        return null;
    }

    @PostMapping(value = "/updateOrder/{orderId}")
    @ResponseBody
    public Orders updateOrder(@RequestBody Orders order, @PathVariable Long orderId){
        return orderService.updateOrder(order, orderId);
    }

    @PostMapping(value = "/deleteOrder/{id}")
    @ResponseBody
    public Long deleteProducts(@PathVariable Long id){
        orderService.deleteOrder(id);
        return id;
    }

}
