package com.hendrickoc.docker_manager.controllers;

import com.github.dockerjava.api.model.Container;
import com.hendrickoc.docker_manager.services.DockerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class DockerContainerController {
    private final DockerService dockerService;

    public DockerContainerController(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    @GetMapping("")
    public List<Container> listContainer(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return dockerService.listContainers(showAll);
    }

    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable String id) {
        dockerService.startContainer(id);
    }

    @PostMapping("/{id}/stop")
    public void stopContainer(@PathVariable String id) {
        dockerService.stopContainer(id);
    }

    @PostMapping("/{id}")
    public void deleteContainer(@PathVariable String id) {
        dockerService.deleteContainer(id);
    }

    @PostMapping("")
    public void createContainer(@PathVariable String imageName) {
        dockerService.createContainer(imageName);
    }


}
