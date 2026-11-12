package com.hendrickoc.docker_manager.services.dockerServices;

import com.github.dockerjava.api.DockerClient;
import com.hendrickoc.docker_manager.dto.ContainerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerService {
    private final DockerClient dockerClient;

    public ContainerService(DockerClient client) {
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
