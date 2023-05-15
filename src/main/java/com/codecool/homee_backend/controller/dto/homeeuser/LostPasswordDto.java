package com.codecool.homee_backend.controller.dto.homeeuser;

public record LostPasswordDto(
        String email,
        Integer passwordCode,
        String changedPassword
) {
}
