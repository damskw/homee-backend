package com.codecool.homee_backend.entity;

import com.codecool.homee_backend.entity.type.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY)
    private Device device;
    private String name;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private Boolean notification;
    private LocalDate notificationTime;
    private LocalDate scheduledAt;

    public Event(String name, EventType eventType, Boolean notification,
                 LocalDate notificationTime, LocalDate scheduledAt) {
        this.name = name;
        this.eventType = eventType;
        this.notification = notification;
        this.notificationTime = notificationTime;
        this.scheduledAt = scheduledAt;
    }


    public Set<HomeeUser> getHomeeUsers() {
        return this.getDevice().getSpace().getHomeeUsers();
    }
}
