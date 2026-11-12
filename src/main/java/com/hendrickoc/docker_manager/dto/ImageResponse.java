package com.hendrickoc.docker_manager.dto;

import java.util.List;

public record ImageResponse(
        String id,
        List<String> repoTags,
        Long size,
        Long created
) {}
