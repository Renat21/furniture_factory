package com.example.factory.repository;

import com.example.factory.entity.Orders;
import com.example.factory.entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    public List<Orders> findOrdersByClientAndOnProduction(User user, Boolean onProduction);

    public Orders findOrdersById(Long id);

    public void deleteOrdersById(Long id);

    public Orders findOrdersByClient(Orders orders);
}
