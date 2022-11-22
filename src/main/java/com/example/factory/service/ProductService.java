package com.example.factory.service;


import com.example.factory.entity.Orders;
import com.example.factory.entity.Product;
import com.example.factory.entity.User;
import com.example.factory.repository.OrderRepository;
import com.example.factory.repository.ProductRepository;
import com.example.factory.repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;


    @Transactional
    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    public List<Product> getUsersProducts(User user){
        List<Orders> orders = orderRepository.findOrdersByClientAndOnProduction(user, true);
        if (orders.size() == 0)
            return new ArrayList<>();
        return orders.get(0).getProducts();
    }

    public List<Product> getProductsByType(String type){
        return productRepository.findProductByType(type);
    }

    public void addProductToOrder(User user, Long id){
        if (orderRepository.findOrdersByClientAndOnProduction(user, true).size() == 0)
            createNewOrder(user);
        Orders order = orderRepository.findOrdersByClientAndOnProduction(user, true).get(0);
        order.getProducts().add(productRepository.findProductById(id));
        orderRepository.save(order);
    }

    public void deleteProductFromOrder(User user, Long id){
        Orders order = orderRepository.findOrdersByClientAndOnProduction(user, true).get(0);
        order.getProducts().remove(productRepository.findProductById(id));
        orderRepository.save(order);
    }

    @Transactional
    public void deleteProductById(Long id){
        productRepository.deleteProductById(id);
    }
    public Product getProductById(Long id){
        return productRepository.findProductById(id);
    }


    public void createNewOrder(User user){
        Orders orders = new Orders();
        orders.setClient(user);
        orders.setOnProduction(true);
        orders.setProducts(new ArrayList<>());
        user.getOrders().add(orders);
        userRepository.save(user);
//        if (user.getOrders() == null)
//            user.setOrders(new ArrayList<>());
//        user.getOrders().add(orders);
//        userRepository.save(user);
    }

    public void buyProducts(User user){
        Orders order = orderRepository.findOrdersByClientAndOnProduction(user, true).get(0);
        order.setOnProduction(false);
        order.setPrice(order.getProducts().stream().mapToInt(i -> Math.toIntExact(i.getCost())).sum());
        order.setName("Заказ №" + order.getId());
        orderRepository.save(order);
//        user.getOrders().add(order);
//        userRepository.save(user);
//        createNewOrder(user);
    }
}
