package com.codecool.homee_backend.controller.dto.event;

import com.codecool.homee_backend.entity.type.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record NewEventDto(
        UUID deviceId,
        String name,
        EventType eventType,
        Boolean notification,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate notificationTime,
        @JsonFormat(pattern="yyyy-MM-dd")
        LocalDate scheduledAt
) {
}
