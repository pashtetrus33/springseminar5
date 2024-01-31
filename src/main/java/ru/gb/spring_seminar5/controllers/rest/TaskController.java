package ru.gb.spring_seminar5.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.models.dto.DtoMapper;
import ru.gb.spring_seminar5.models.dto.TaskDto;
import ru.gb.spring_seminar5.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final DtoMapper dtoMapper;

    @Operation(summary = "Получить все задачи")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks().stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "Получить задачу по id")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(dtoMapper.toDto(taskService.getTaskById(id)));
    }

    @Operation(summary = "Создать задачу")
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Validated TaskDto newTask, BindingResult bindingResult) {
        Task addedTask = taskService.createTask(dtoMapper.toEntity(newTask));
        return ResponseEntity.ok().body(dtoMapper.toDto(addedTask));
    }

    @Operation(summary = "Обновить задачу")
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable("id") Long id, @RequestBody TaskDto task) {
        Task updateTask = taskService.updateTask(id, dtoMapper.toEntity(task));
        return ResponseEntity.ok().body(dtoMapper.toDto(updateTask));
    }

    @Operation(summary = "Удалить задачу по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().body("Task " + id + " is successfully deleted!");
    }

    @Operation(summary = "Получить задачи по статусу")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDto>> getTasksByStatus(@PathVariable("status") String sts) {
        List<TaskDto> tasks = taskService.getTasksByStatus(sts)
                .stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "Обновить статус задачи")
    @GetMapping("/upd/{id}")
    public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id) {
        Task task = taskService.updateStatus(id);
        return ResponseEntity.ok().body(dtoMapper.toDto(task));
    }
}