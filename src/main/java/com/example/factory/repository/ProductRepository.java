package com.example.factory.repository;


import com.example.factory.entity.Orders;
import com.example.factory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findProductByType(String type);

    public Product findProductById(Long id);

    public void deleteProductById(Long id);

}
