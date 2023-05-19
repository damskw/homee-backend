package com.codecool.homee_backend.repository;

import com.codecool.homee_backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT  DISTINCT t " +
            "FROM Task t " +
            "JOIN FETCH t.device d " +
            "JOIN FETCH t.homeeUser u " +
            "JOIN FETCH d.space s " +
            "WHERE u.id = :userId")
    List<Task> findAllByHomeeUserId(UUID userId);

    @Query("SELECT t " +
            "FROM Task t " +
            "JOIN FETCH t.device d " +
            "JOIN FETCH d.space s " +
            "JOIN FETCH t.homeeUser u " +
            "WHERE u.id = :userId AND s.id = :spaceId")
    List<Task> findAllByUserIdAndSpaceId(UUID userId, UUID spaceId);

    @Query("SELECT DISTINCT t " +
            "FROM Task t " +
            "JOIN FETCH t.homeeUser u " +
            "WHERE t.deadline > :todayDate")
    List<Task> findActiveTasks(LocalDate todayDate);
}
