package com.codecool.homee_backend.controller.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record NewTaskDto(
        String name,
        String description,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate deadline,
        UUID userId,
        UUID deviceId
) {
}
