package com.crois.course.controller;

import com.crois.course.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/images")
@AllArgsConstructor
public class Image {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<InputStreamResource> getImageById(@PathVariable("id") UUID id) throws Exception {
        return imageService.getImage(id);
    }

}
