package com.hendrickoc.docker_manager.services;

import com.github.dockerjava.api.DockerClient;
import com.hendrickoc.docker_manager.dto.ContainerResponse;
import com.hendrickoc.docker_manager.dto.ImageResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DockerService {
    private final DockerClient dockerClient;

    public DockerService(DockerClient client) {
        this.dockerClient = client;
    }

    public List<ContainerResponse> listContainers(boolean all) {
        return dockerClient.listContainersCmd().withShowAll(all).exec()
                .stream()
                .map(c -> new ContainerResponse(
                        c.getId().substring(0, 12),
                        c.getNames()[0].replace("/", ""),
                        c.getImage(),
                        c.getState(),
                        c.getStatus()
                )).toList();
    }

    public List<ImageResponse> listImages(){
        return dockerClient.listImagesCmd().exec()
                .stream()
                .map(this::mapToImageResponse)
                .toList();
    }

    public List<ImageResponse> filterImages(String filtername){
        return dockerClient.listImagesCmd().withImageNameFilter(filtername).exec()
                .stream()
                .map(this::mapToImageResponse)
                .toList();
    }

    private ImageResponse mapToImageResponse(com.github.dockerjava.api.model.Image img) {
        List<String> tags = img.getRepoTags() != null
                ? Arrays.asList(img.getRepoTags()) : List.of("<none>:<none>");

        String cleanId = img.getId().replace("sha256:", "");
        String shortId = cleanId.length() > 12 ? cleanId.substring(0, 12) : cleanId;

        return new ImageResponse(shortId, tags, img.getSize(), img.getCreated());
    }

    public void startContainer(String containerId) {
        try (var command = dockerClient.startContainerCmd(containerId)) {
            command.exec();
        }
    }

    public void stopContainer(String containerId) {
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void deleteContainer(String containerId) {
        try (var command = dockerClient.removeContainerCmd(containerId)) {
            command.exec();
        }
    }

    public String createContainer(String imageName) {
        try (var command = dockerClient.createContainerCmd(imageName)) {
            return command.exec().getId();
        }
    }
}
