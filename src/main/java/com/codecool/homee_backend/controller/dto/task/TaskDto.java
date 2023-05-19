package com.codecool.homee_backend.controller.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record TaskDto(
    UUID id,
    UUID spaceId,
    UUID deviceId,
    String spaceName,
    String deviceName,
    String name,
    String description,
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate creationDate,
    @JsonFormat(pattern="yyyy-MM-dd")
    LocalDate deadline,
    Boolean isDone,
    Long daysLeft
) { }
