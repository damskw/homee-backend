package com.codecool.homee_backend.controller.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeviceDto (
        UUID id,
        String name,
        String model,
        String deviceType,
        String spot,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate warrantyStart,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate warrantyEnd,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate purchaseDate,
        Double purchasePrice,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm")
        LocalDateTime createdAt,
        @JsonFormat(pattern="yyyy-MM-dd HH:mm")
        LocalDateTime updatedAt,
        String about,
        String imageName
) {

}
