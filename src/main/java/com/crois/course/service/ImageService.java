package com.crois.course.service;

import com.crois.course.entity.ImageEntity;
import com.crois.course.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    private final MinioService minioService;

    public ResponseEntity<InputStreamResource> getImage(@PathVariable UUID id) throws Exception {

        ImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Image not found"
                ));

        InputStream imageStream = minioService.get(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getHttpContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(imageStream));
    }

}
