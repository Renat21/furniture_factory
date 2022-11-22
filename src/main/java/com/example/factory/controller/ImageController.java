package com.example.factory.controller;

import com.example.factory.entity.Image;
import com.example.factory.repository.ImageRepository;
import com.example.factory.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ImageService imageService;

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id){
        Image image = imageRepository.findImageById(id);
        return ResponseEntity.ok().header("fileName",
                image.getOriginalFileName()).contentType(
                MediaType.valueOf(image.getContentType())).contentLength(image.getSize()).
                body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }


    @RequestMapping(value = "/image/create", method = RequestMethod.POST)
    @ResponseBody
    public List<Long> processReloadData(@RequestParam("file") List<MultipartFile> files) {
        return files.stream().map(file -> {
            try {
                Image image = imageService.toImageEntity(file);
                imageService.save(image);
                return image.getId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
