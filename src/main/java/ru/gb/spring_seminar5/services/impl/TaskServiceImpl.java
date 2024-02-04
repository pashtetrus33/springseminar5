package ru.gb.spring_seminar5.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.spring_seminar5.exceptions.StatusNotFoundException;
import ru.gb.spring_seminar5.exceptions.TaskNotFoundException;
import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.repositoreis.TaskRepository;
import ru.gb.spring_seminar5.services.ExecutorService;
import ru.gb.spring_seminar5.services.TaskService;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Сервис работы с задачами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ExecutorService executorService;

    @Override
    public List<Task> getAllTasks() {
        log.info("Get all tasks command");
        return taskRepository.findAll().stream().sorted(Comparator.comparing(Task:: getCreatedAt)).toList();
    }

    @Override
    public Task getTaskById(Long id) {
        log.info("Get task by id " + id + " command");

        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task: " + id + " not found!"));
    }

    @Override
    public Task createTask(Task task) {
        log.info("Create task command");
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedString = ldt.format(formatter);
        task.setCreatedAt(LocalDateTime.parse(formattedString, formatter));
        task.setStatus(Status.NOT_STARTED);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        log.info("Update task with id " + id + " command");
        if (taskRepository.findById(id).isPresent()) {
            Task updatedTask = taskRepository.findById(id).get();
            updatedTask.setTitle(task.getTitle());
            if (task.getStatus() != null) {
                updatedTask.setStatus(task.getStatus());
            }
            updatedTask.setDescription(task.getDescription());
            LocalDateTime ldt = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedString = ldt.format(formatter);
            updatedTask.setCreatedAt(LocalDateTime.parse(formattedString, formatter));
            taskRepository.save(updatedTask);
            return updatedTask;
        }

        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Delete task with id " + id + " command");
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            log.info("Delete task with id " + id + " success");
            return;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }

    @Override
    public Task updateStatus(Long id) {
        log.info("Update status command id " + id);
        if (taskRepository.findById(id).isPresent()) {
            Task task = taskRepository.findById(id).get();
            if (Objects.requireNonNull(task.getStatus()) == Status.NOT_STARTED) {
                task.setStatus(Status.IN_PROGRESS);
            } else {
                task.setStatus(Status.COMPLETED);
            }
            taskRepository.save(task);
            return task;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }

    @Override
    public List<Task> getTasksByStatus(String sts) {
        log.info("Get tasks by status " + sts + " command");
        Status status;
        for (Status s : Status.values()) {
            if (s.getTitle().equalsIgnoreCase(sts)) {
                status = s;
                return taskRepository.findByStatus(status);
            }
        }
        throw new StatusNotFoundException("Status " + sts + " is not found.");
    }

    @Override
    public Task assignExecutor(Long taskId, Long id) {
        log.info("Assign executor to the task #" + taskId + "executor id #" + id);
        if (taskRepository.findById(taskId).isPresent()) {
            Task task = taskRepository.findById(taskId).get();
            task.setExecutor(executorService.getExecutorById(id));
            taskRepository.save(task);
            return task;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }
}
