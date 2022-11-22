package com.example.factory.service;


import com.example.factory.entity.Image;
import com.example.factory.entity.Product;
import com.example.factory.entity.User;
import com.example.factory.repository.ImageRepository;
import com.example.factory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    @Autowired
    private UserService userService;


    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Image toImageEntity(MultipartFile file) throws IOException {
        return new Image(file.getName(), file.getOriginalFilename(), file.getSize(), file.getContentType(),
                file.getBytes());
    }

    public void save(Image image) {
        imageRepository.save(image);
    }


    public Image findImageById(Long id){
        return imageRepository.findImageById(id);
    }
}