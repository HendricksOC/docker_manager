package com.hendrickoc.docker_manager.services.dockerServices;

import com.github.dockerjava.api.DockerClient;
import com.hendrickoc.docker_manager.dto.ImageResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ImageService {
        public DockerClient dockerClient;

    public ImageService(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    private ImageResponse mapToImageResponse(com.github.dockerjava.api.model.Image img) {
        List<String> tags = img.getRepoTags() != null
                ? Arrays.asList(img.getRepoTags()) : List.of("<none>:<none>");

        String cleanId = img.getId().replace("sha256:", "");
        String shortId = cleanId.length() > 12 ? cleanId.substring(0, 12) : cleanId;

        return new ImageResponse(shortId, tags, img.getSize(), img.getCreated());
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
}
