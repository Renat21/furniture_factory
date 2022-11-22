package com.example.factory.service;


import com.example.factory.entity.Orders;
import com.example.factory.entity.Product;
import com.example.factory.entity.User;
import com.example.factory.repository.OrderRepository;
import com.example.factory.repository.ProductRepository;
import com.example.factory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public Orders findOrderById(Long id){
        return orderRepository.findOrdersById(id);
    }

    public void save(Orders orders){

    }

    public Orders updateOrder(Orders orders, Long id){
        Orders dbOrder = orderRepository.findOrdersById(id);
        dbOrder.setName(orders.getName());
        dbOrder.setPrice(orders.getPrice());
        orderRepository.save(dbOrder);
        return dbOrder;
    }

    public void deleteOrder(Long id){
        Orders order = orderRepository.findOrdersById(id);
        for (int i = 0; i < order.getProducts().size(); i++){
            Product product = productRepository.findProductById(order.getProducts().get(order.getProducts().size() - i - 1).getId());
            product.getOrders().remove(order);
//            productRepository.save(product);
        }

        User user = userRepository.findUserById(order.getClient().getId());
        user.getOrders().remove(order);
//        userRepository.save(user);
//        orderRepository.delete(orderRepository.findOrdersById(order.getId()));
    }
}
