package ru.gb.spring_seminar5.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.gb.spring_seminar5.aspects.LoggedExecution;
import ru.gb.spring_seminar5.aspects.TrackUserAction;
import ru.gb.spring_seminar5.exceptions.StatusNotFoundException;
import ru.gb.spring_seminar5.exceptions.TaskNotFoundException;
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.repositoreis.TaskRepository;
import ru.gb.spring_seminar5.services.ExecutorService;
import ru.gb.spring_seminar5.services.TaskService;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Сервис работы с задачами
 * Примените паттерн Singleton для создания сервиса управления задачами.
 */
@Service
@Scope("singleton")
@RequiredArgsConstructor
public class UrgentTaskServiceImpl implements TaskService {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final TaskRepository taskRepository;
    private final ExecutorService executorService;

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


    @TrackUserAction(level = Level.INFO)
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll().stream().sorted(Comparator.comparing(Task::getCreatedAt)).toList();
    }

    @TrackUserAction(level = Level.INFO)
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task: " + id + " not found!"));
    }

    @TrackUserAction(level = Level.INFO)
    @Override
    public Task createTask(Task task) {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedString = ldt.format(formatter);
        task.setCreatedAt(LocalDateTime.parse(formattedString, formatter));
        task.setStatus(Status.URGENT);
        task.setExecutor(executorService.getExecutorById(1L));
        return taskRepository.save(task);
    }

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public Task updateTask(Long id, Task task) {
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

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public void deleteTask(Long id) {
        if (taskRepository.findById(id).isPresent()) {
            taskRepository.deleteById(id);
            return;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public Task updateStatus(Long id) {
        Status currentStatus;
        if (taskRepository.findById(id).isPresent()) {
            Task task = taskRepository.findById(id).get();
            if (Objects.requireNonNull(task.getStatus()) == Status.URGENT) {
                currentStatus = Status.URGENT;
                task.setStatus(Status.IN_PROGRESS);
            } else {
                currentStatus = Status.IN_PROGRESS;
                task.setStatus(Status.COMPLETED);
            }
            support.firePropertyChange("status", currentStatus, task.getStatus());
            taskRepository.save(task);
            return task;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public List<Task> getTasksByStatus(String sts) {
        Status status;
        for (Status s : Status.values()) {
            if (s.getTitle().equalsIgnoreCase(sts)) {
                status = s;
                return taskRepository.findByStatus(status);
            }
        }
        throw new StatusNotFoundException("Status " + sts + " is not found.");
    }

    @LoggedExecution
    @TrackUserAction(level = Level.INFO)
    @Override
    public Task assignExecutor(Long taskId, Long id) {
        if (taskRepository.findById(taskId).isPresent()) {
            Task task = taskRepository.findById(taskId).get();
            Executor currentExecutor = task.getExecutor();
            task.setExecutor(executorService.getExecutorById(id));
            support.firePropertyChange("executor", currentExecutor, task.getExecutor());
            taskRepository.save(task);
            return task;
        }
        throw new TaskNotFoundException("Task with id " + id + " is not found.");
    }
}
