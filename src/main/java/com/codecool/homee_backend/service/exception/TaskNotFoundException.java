package com.codecool.homee_backend.service.exception;

import java.util.UUID;

public class TaskNotFoundException extends ResourcesNotFoundException {

    public TaskNotFoundException(UUID id) { super("Task with id " + id + " not found."); }

}
