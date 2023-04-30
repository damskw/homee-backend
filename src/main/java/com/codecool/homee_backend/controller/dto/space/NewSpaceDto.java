package com.codecool.homee_backend.controller.dto.space;

import java.util.UUID;

public record NewSpaceDto(
    String name,
    String about,
    UUID userId
) {
}
