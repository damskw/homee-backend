package com.codecool.homee_backend.repository;

import com.codecool.homee_backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> getEventsByDeviceId(UUID id);

    @Query("SELECT DISTINCT e " +
            "FROM Event e " +
            "JOIN FETCH e.device d " +
            "JOIN FETCH d.space s " +
            "JOIN FETCH s.homeeUsers " +
            "WHERE e.notificationTime = :date")
    List<Event> getAllByNotificationTime(LocalDate date);

    @Query("SELECT e FROM Event e JOIN e.device d JOIN d.space s JOIN s.homeeUsers u WHERE u.id =:id")
    List<Event> findAllByUserId(UUID id);

    @Query("SELECT e FROM Event e JOIN e.device d WHERE d.id =:id AND e.scheduledAt < :time")
    List<Event> findAllByDeviceIdOlderThan(UUID id, LocalDate time);

    @Query("SELECT e FROM Event e JOIN e.device d WHERE d.id =:id AND e.scheduledAt > :time")
    List<Event> findAllByDeviceIdLaterThan(UUID id, LocalDate time);

    @Query("SELECT COUNT(e) FROM HomeeUser u JOIN u.spaces s JOIN s.devices d JOIN d.events e WHERE u.id = :id AND e.notification=true")
    Integer countUserNotificationEvents(UUID id);

}
