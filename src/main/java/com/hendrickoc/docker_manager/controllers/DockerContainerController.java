package com.hendrickoc.docker_manager.controllers;

import com.hendrickoc.docker_manager.dto.ContainerResponse;
import com.hendrickoc.docker_manager.services.dockerServices.ContainerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class DockerContainerController {
    private final ContainerService containerService;

    public DockerContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping("")
    public List<ContainerResponse> listContainer(@RequestParam(required = false, defaultValue = "true") boolean showAll) {
        return containerService.listContainers(showAll);
    }

    @PostMapping("/{id}/start")
    public void startContainer(@PathVariable String id) {
        containerService.startContainer(id);
    }

    @PostMapping("/{id}/stop")
    public void stopContainer(@PathVariable String id) {
        containerService.stopContainer(id);
    }

    @DeleteMapping("/{id}")
    public void deleteContainer(@PathVariable String id) {
        containerService.deleteContainer(id);
    }

    @PostMapping("")
    public String createContainer(@RequestParam String imageName) {
        return containerService.createContainer(imageName);
    }


}
