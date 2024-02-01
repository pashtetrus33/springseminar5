package ru.gb.spring_seminar5.models.dto;

import org.springframework.stereotype.Component;
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.models.Task;

/**
 * Маппер преобразования объектов dto в сущности и наоборот.
 */
@Component
public class DtoMapper {
    /**
     * Преобразование в dto.
     *
     * @param task объект задачи.
     * @return объект dto.
     */
    public TaskDto toDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setStatus(task.getStatus());
        taskDto.setExecutor(task.getExecutor());
        return taskDto;
    }

    /**
     * Преобразование в dto.
     *
     * @param executor объект задачи.
     * @return объект dto.
     */
    public ExecutorDto toDto(Executor executor) {
        ExecutorDto executorDto = new ExecutorDto();
        executorDto.setId(executor.getId());
        executorDto.setName(executor.getName());
        executorDto.setAge(executor.getAge());
        return executorDto;
    }

    /**
     * Преобразование в объект сущности.
     *
     * @param taskDto объект dto.
     * @return объект сущности.
     */
    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCreatedAt(taskDto.getCreatedAt());
        task.setStatus(taskDto.getStatus());
        return task;
    }

    /**
     * Преобразование в объект сущности.
     *
     * @param executorDto объект dto.
     * @return объект сущности.
     */
    public Executor toEntity(ExecutorDto executorDto) {
        Executor executor = new Executor();
        executor.setId(executorDto.getId());
        executor.setName(executorDto.getName());
        executor.setAge(executorDto.getAge());
        return executor;
    }
}