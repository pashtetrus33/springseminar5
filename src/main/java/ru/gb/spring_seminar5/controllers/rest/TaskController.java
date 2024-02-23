package ru.gb.spring_seminar5.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring_seminar5.enums.TaskType;
import ru.gb.spring_seminar5.factories.CustomTaskFactory;
import ru.gb.spring_seminar5.models.Status;
import ru.gb.spring_seminar5.models.Task;
import ru.gb.spring_seminar5.models.dto.DtoMapper;
import ru.gb.spring_seminar5.models.dto.TaskDto;
import ru.gb.spring_seminar5.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {


    private final CustomTaskFactory customTaskFactory;
    private final DtoMapper dtoMapper;

    @Operation(summary = "Получить все задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all the tasks",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))})})
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        List<TaskDto> tasks = taskService.getAllTasks().stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "Получить задачу по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the task",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@Parameter(description = "id of task to be searched")
                                           @PathVariable("id") Long id) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        return ResponseEntity.ok().body(dtoMapper.toDto(taskService.getTaskById(id)));
    }

    @Operation(summary = "Создать задачу")
    @ApiResponse(responseCode = "200", description = "Create the task",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class))})
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Validated TaskDto newTask, BindingResult bindingResult) {
        TaskService taskService;
        if (newTask.getTitle().startsWith("CРОЧНО ") || newTask.getTitle().startsWith("URGENT ")) {
            taskService = customTaskFactory.create(TaskType.URGENT);
        } else {
            taskService = customTaskFactory.create(TaskType.NORMAL);
        }

        Task addedTask = taskService.createTask(dtoMapper.toEntity(newTask));
        return ResponseEntity.ok().body(dtoMapper.toDto(addedTask));
    }

    @Operation(summary = "Обновить задачу")
    @ApiResponse(responseCode = "200", description = "Update the task",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class))})
    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@Parameter(description = "id of task to be updated")
                                              @PathVariable("id") Long id, @RequestBody TaskDto task) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        Task updateTask = taskService.updateTask(id, dtoMapper.toEntity(task));
        return ResponseEntity.ok().body(dtoMapper.toDto(updateTask));
    }

    @Operation(summary = "Удалить задачу по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@Parameter(description = "id of task to be deleted")
                                             @PathVariable("id") Long id) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        taskService.deleteTask(id);
        return ResponseEntity.ok().body("Task " + id + " is successfully deleted!");
    }

    @Operation(summary = "Получить задачи по статусу")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TaskDto>> getTasksByStatus(@Parameter(description = "status of task to be filtered")
                                                          @PathVariable("status") String sts) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        List<TaskDto> tasks = taskService.getTasksByStatus(sts)
                .stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(tasks);
    }

    @Operation(summary = "Обновить статус задачи")
    @GetMapping("/upd/{id}")
    public ResponseEntity<TaskDto> updateTaskStatus(@Parameter(description = "id of task which status to be updated")
                                                    @PathVariable Long id) {
        TaskService taskService = customTaskFactory.create(TaskType.NORMAL);
        if (taskService.getTaskById(id).getStatus().equals(Status.URGENT)) {
            taskService = customTaskFactory.create(TaskType.URGENT);
        }
        Task task = taskService.updateStatus(id);
        return ResponseEntity.ok().body(dtoMapper.toDto(task));
    }
}