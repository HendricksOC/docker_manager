package com.hendrickoc.docker_manager.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class  DockerService {
    private final DockerClient dockerClient;

    public DockerService(DockerClient client){
        this.dockerClient = client;
    }

    public List<Container> listContainers(boolean all){
        return dockerClient.listContainersCmd().withShowAll(all).exec();
    }

    public List<Image> listImages(){
        return dockerClient.listImagesCmd().exec();
    }
    public List<Image> filterImages(String filtername){
        return dockerClient.listImagesCmd().withImageNameFilter(filtername).exec();
    }
    public void startContainer(String containerId){
        dockerClient.startContainerCmd(containerId).exec();
    }
    public void stopContainer(String containerId){
        dockerClient.stopContainerCmd(containerId).exec();
    }

    public void deleteContainer(String containerId){
        dockerClient.removeContainerCmd(containerId);
    }
    public void createContainer(String containerId){
        dockerClient.createContainerCmd(containerId).exec();
    }
}
