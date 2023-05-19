package com.codecool.homee_backend.mapper;

import com.codecool.homee_backend.controller.dto.task.NewTaskDto;
import com.codecool.homee_backend.controller.dto.task.TaskDto;
import com.codecool.homee_backend.entity.Task;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class TaskMapper {

    public Task mapTaskDtoToEntity(NewTaskDto dto) {
        return new Task(
                dto.name(),
                dto.description(),
                dto.deadline()
        );
    }

    public TaskDto mapTaskEntityToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getSpaceId(),
                task.getDeviceId(),
                task.getSpaceName(),
                task.getDeviceName(),
                task.getName(),
                task.getDescription(),
                task.getCreationDate(),
                task.getDeadline(),
                task.getIsDone(),
                ChronoUnit.DAYS.between(task.getCreationDate(), task.getDeadline())
        );
    }


}
