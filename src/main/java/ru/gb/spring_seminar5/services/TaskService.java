package ru.gb.spring_seminar5.services;

import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task task);

    void deleteTask(Long id);

    Task updateStatus(Long id);

    List<Task> getTasksByStatus(String status);
}
