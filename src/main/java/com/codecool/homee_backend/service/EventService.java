package com.codecool.homee_backend.service;

import com.codecool.homee_backend.controller.dto.event.EventDto;
import com.codecool.homee_backend.controller.dto.event.NewEventDto;
import com.codecool.homee_backend.controller.dto.event.UpdatedEvent;
import com.codecool.homee_backend.entity.Device;
import com.codecool.homee_backend.entity.DeviceActivity;
import com.codecool.homee_backend.entity.Event;
import com.codecool.homee_backend.entity.type.ActivityType;
import com.codecool.homee_backend.entity.type.EventType;
import com.codecool.homee_backend.mapper.EventMapper;
import com.codecool.homee_backend.repository.DeviceRepository;
import com.codecool.homee_backend.repository.EventRepository;
import com.codecool.homee_backend.service.exception.DeviceNotFoundException;
import com.codecool.homee_backend.service.exception.EventNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DeviceRepository deviceRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, DeviceRepository deviceRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.deviceRepository = deviceRepository;
        this.eventMapper = eventMapper;
    }

    public List<Event> getEventEntitiesForDate(LocalDate date) {
        return eventRepository.getAllByNotificationTime(date);
    }


    public List<EventDto> getEventsForDate(LocalDate date) {
        return eventRepository.getAllByNotificationTime(date).stream()
                .map(eventMapper::mapEventEntityToDto)
                .toList();
    }


    public EventDto getSingleEvent(UUID id) {
        return eventRepository.findById(id)
                .map(eventMapper::mapEventEntityToDto)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    public List<EventDto> getEventsForDevice(UUID deviceId) {
        return eventRepository.getEventsByDeviceId(deviceId).stream()
                .map(eventMapper::mapEventEntityToDto)
                .toList();
    }

    public void deleteEvent(UUID id) {
        eventRepository.deleteById(id);
    }

    public List<EventType> getTypes() {
        return List.of(EventType.values());
    }

    public EventDto updateEvent(UpdatedEvent updatedEvent) {
        Event event = eventRepository.findById(updatedEvent.eventId())
                .orElseThrow(() -> new EventNotFoundException(updatedEvent.eventId()));
        event.setName(updatedEvent.name());
        event.setEventType(updatedEvent.eventType());
        event.setNotification(updatedEvent.notification());
        event.setScheduledAt(updatedEvent.scheduledAt());
        Event eventDb = eventRepository.save(event);
        return eventMapper.mapEventEntityToDto(eventDb);
    }

    public EventDto addNewEvent(NewEventDto newEvent) {
        Device device = deviceRepository.findById(newEvent.deviceId())
                .orElseThrow(() -> new DeviceNotFoundException(newEvent.deviceId()));
        Event event = eventMapper.mapEventDtoToEntity(newEvent);
        addCreatedNewEventActivity(device, event);
        event.setDevice(device);
        Event eventDb = eventRepository.save(event);
        return eventMapper.mapEventEntityToDto(eventDb);
    }

    private void addCreatedNewEventActivity(Device device, Event event) {
        DeviceActivity deviceActivity = new DeviceActivity(
                device,
                createAddNewEventDescription(event),
                ActivityType.REMINDER
        );
        device.addActivity(deviceActivity);
    }

    private String createAddNewEventDescription(Event event) {
        return "Event " + event.getName() + " has been added to device.";
    }

    public List<EventDto> getFutureEvents(UUID deviceId) {
        return eventRepository.findAllByDeviceIdLaterThan(deviceId, LocalDate.now())
                .stream()
                .map(eventMapper::mapEventEntityToDto)
                .toList();
    }

    public List<EventDto> getPastEvents(UUID deviceId) {
        return eventRepository.findAllByDeviceIdOlderThan(deviceId, LocalDate.now())
                .stream()
                .map(eventMapper::mapEventEntityToDto)
                .toList();
    }

    public Integer getNumberOfNotificationEventsForUser(UUID userId) {
        return eventRepository.countUserNotificationEvents(userId);
    }
}
