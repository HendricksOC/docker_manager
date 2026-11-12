package com.hendrickoc.docker_manager.dto;

public record ContainerResponse(
        String id,
        String name,
        String image,
        String state,
        String status

) {}
