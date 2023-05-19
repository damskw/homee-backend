package com.codecool.homee_backend.service;

import com.codecool.homee_backend.controller.dto.notification.NewNotificationDto;
import com.codecool.homee_backend.controller.dto.task.NewTaskDto;
import com.codecool.homee_backend.controller.dto.task.TaskDto;
import com.codecool.homee_backend.entity.Device;
import com.codecool.homee_backend.entity.HomeeUser;
import com.codecool.homee_backend.entity.Task;
import com.codecool.homee_backend.mapper.TaskMapper;
import com.codecool.homee_backend.repository.DeviceRepository;
import com.codecool.homee_backend.repository.HomeeUserRepository;
import com.codecool.homee_backend.repository.TaskRepository;
import com.codecool.homee_backend.service.exception.DeviceNotFoundException;
import com.codecool.homee_backend.service.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final HomeeUserRepository homeeUserRepository;
    private final DeviceRepository deviceRepository;
    private final NotificationService notificationService;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, HomeeUserRepository homeeUserRepository, DeviceRepository deviceRepository, NotificationService notificationService, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.homeeUserRepository = homeeUserRepository;
        this.deviceRepository = deviceRepository;
        this.notificationService = notificationService;
        this.taskMapper = taskMapper;
    }

    public TaskDto getTask(UUID id) {
        return taskRepository.findById(id)
                .map(taskMapper::mapTaskEntityToDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<TaskDto> getTasksForUser(UUID userId) {
        return taskRepository.findAllByHomeeUserId(userId).stream()
                .map(taskMapper::mapTaskEntityToDto)
                .toList();
    }

    public List<TaskDto> getTasksForUserAndSpace(UUID userId, UUID spaceId) {
        return taskRepository.findAllByUserIdAndSpaceId(userId, spaceId).stream()
                .map(taskMapper::mapTaskEntityToDto)
                .toList();
    }

    public List<Task> getActiveTasks() {
        return taskRepository.findActiveTasks(LocalDate.now());
    }

    public TaskDto addNewTask(NewTaskDto dto) {
        Task task = taskMapper.mapTaskDtoToEntity(dto);
        HomeeUser homeeUser = homeeUserRepository.findByUserId(dto.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Device device = deviceRepository.findById(dto.deviceId())
                .orElseThrow(() -> new DeviceNotFoundException(dto.deviceId()));
        notificationService.addNewNotification(
                new NewNotificationDto(
                        homeeUser.getId(),
                        getAddedNewTaskNotificationText(task)
                )
        );
        task.setHomeeUser(homeeUser);
        task.setDevice(device);
        return taskMapper.mapTaskEntityToDto(taskRepository.save(task));
    }

    public void deleteTask(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public void markAsDone(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setIsDone(true);
        taskRepository.save(task);
    }

    private String getAddedNewTaskNotificationText(Task task) {
        return String.format("You have been assigned a new task %s.", task.getName());
    }
}
