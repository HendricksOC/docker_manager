package com.hendrickoc.docker_manager.controllers;

import com.hendrickoc.docker_manager.dto.ImageResponse;
import com.hendrickoc.docker_manager.services.DockerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/images")
public class DockerImagesController {
    private final DockerService dockerService;

    public DockerImagesController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping()
    public List<ImageResponse> listImages(){
        return dockerService.listImages();
    }

    @GetMapping("/filter")
    public List<ImageResponse> filterImages(@RequestParam(required = false, defaultValue = "image") String filtername){
        return dockerService.filterImages(filtername);
    }

}
