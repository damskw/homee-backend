package com.codecool.homee_backend.controller;

import com.codecool.homee_backend.controller.dto.task.NewTaskDto;
import com.codecool.homee_backend.controller.dto.task.TaskDto;
import com.codecool.homee_backend.service.TaskService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

import static com.codecool.homee_backend.config.auth.SpringSecurityConfig.USER;

@RolesAllowed(USER)
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable UUID id) {
        return taskService.getTask(id);
    }

    @GetMapping(params = "userId")
    public List<TaskDto> getTasksForUser(@RequestParam UUID userId) {
        return taskService.getTasksForUser(userId);
    }

    @GetMapping(params = {"userId", "spaceId"})
    public List<TaskDto> getTasksForUserAndSpace(@RequestParam UUID userId, @RequestParam UUID spaceId) {
        return taskService.getTasksForUserAndSpace(userId, spaceId);
    }

    @PutMapping(value = "/{id}", params = "done")
    public void markAsDone(@PathVariable UUID id) {
        taskService.markAsDone(id);
    }

    @PostMapping
    public TaskDto addNewTask(@RequestBody NewTaskDto dto) {
        return taskService.addNewTask(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }

}
