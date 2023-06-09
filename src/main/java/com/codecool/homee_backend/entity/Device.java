package com.codecool.homee_backend.entity;

import com.codecool.homee_backend.entity.type.DeviceType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Device {
    @Id
    @EqualsAndHashCode.Include
    private UUID id  = UUID.randomUUID();
    @NotBlank(message = "Cannot be empty.")
    private String name;
    @NotBlank(message = "Cannot be empty.")
    private String model;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    private String spot;
    private LocalDate warrantyStart;
    private LocalDate warrantyEnd;
    private LocalDate purchaseDate;
    private String imageName;
    private Double purchasePrice;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Space space;
    @OneToMany(mappedBy = "device", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Note> notes = new ArrayList<>();
    @OneToMany(mappedBy = "device", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<DeviceActivity> deviceActivities = new ArrayList<>();
    @OneToMany(mappedBy = "device", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Event> events = new ArrayList<>();
    @OneToMany(mappedBy = "device", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> tasks = new ArrayList<>();
    @OneToMany(mappedBy = "device", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();
    private String about;

    public Device(String name, String model, DeviceType deviceType, String spot, LocalDate warrantyStart, LocalDate warrantyEnd, LocalDate purchaseDate, Double purchasePrice, String about) {
        this.name = name;
        this.model = model;
        this.deviceType = deviceType;
        this.spot = spot;
        this.warrantyStart = warrantyStart;
        this.warrantyEnd = warrantyEnd;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.about = about;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void addActivity(DeviceActivity deviceActivity) {
        deviceActivities.add(deviceActivity);
    }

    public void addDocument(Document document) { this.documents.add(document); }

    public void addTask(Task task) {
        this.tasks.add(task);
    }
}
