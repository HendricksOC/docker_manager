package com.hendrickoc.docker_manager.controllers;

import com.hendrickoc.docker_manager.dto.ImageResponse;
import com.hendrickoc.docker_manager.services.dockerServices.ImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class DockerImagesController {
    private final ImageService imageService;

    public DockerImagesController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping()
    public List<ImageResponse> listImages(){
        return imageService.listImages();
    }

    @GetMapping("/filter")
    public List<ImageResponse> filterImages(@RequestParam(required = false, defaultValue = "image") String filtername){
        return imageService.filterImages(filtername);
    }

}
