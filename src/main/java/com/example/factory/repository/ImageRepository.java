package com.example.factory.repository;


import com.example.factory.entity.Image;
import com.example.factory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageById(Long id);
}
