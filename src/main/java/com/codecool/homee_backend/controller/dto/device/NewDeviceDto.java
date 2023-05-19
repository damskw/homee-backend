package com.codecool.homee_backend.controller.dto.device;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record NewDeviceDto(
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
        String about
) {

}
