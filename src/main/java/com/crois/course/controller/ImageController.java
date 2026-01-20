package com.crois.course.controller;

import com.crois.course.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/api/images/")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{uuid}")
    public ResponseEntity<InputStreamResource> readChunk(
            @PathVariable UUID uuid
    ) throws Exception {
        return imageService.getImage(uuid);
    }

}
