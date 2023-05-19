package com.codecool.homee_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    private HomeeUser homeeUser;
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private LocalDate creationDate = LocalDate.now();
    @NotNull
    private LocalDate deadline;
    private Boolean isDone = false;

    public Task(String name, String description, LocalDate deadline) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
    }

    public UUID getSpaceId() {
        return this.getDevice().getSpace().getId();
    }

    public UUID getDeviceId() {
        return this.getDevice().getId();
    }

    public String getDeviceName() {
        return this.getDevice().getName();
    }

    public String getSpaceName() {
        return this.getDevice().getSpace().getName();
    }

}
