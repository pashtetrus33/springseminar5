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
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.models.dto.DtoMapper;
import ru.gb.spring_seminar5.models.dto.ExecutorDto;
import ru.gb.spring_seminar5.services.ExecutorService;

import java.util.List;

@RestController
@RequestMapping("/api/executors")
@RequiredArgsConstructor
public class ExecutorController {

    private final ExecutorService executorService;
    private final DtoMapper dtoMapper;

    @Operation(summary = "Получить всех исполнителей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find all the executors",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExecutorDto.class))})})
    @GetMapping
    public ResponseEntity<List<ExecutorDto>> getAllExecutors() {
        List<ExecutorDto> executors = executorService.getAllExecutors().stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(executors);
    }

    @Operation(summary = "Получить исполнителя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the executor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExecutorDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Executor not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ExecutorDto> getExecutor(@Parameter(description = "id of executor to be searched")
                                                   @PathVariable("id") Long id) {
        return ResponseEntity.ok().body(dtoMapper.toDto(executorService.getExecutorById(id)));
    }

    @Operation(summary = "Создать исполнителя")
    @ApiResponse(responseCode = "200", description = "Create the executor",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExecutorDto.class))})
    @PostMapping
    public ResponseEntity<ExecutorDto> createExecutor(@RequestBody @Validated ExecutorDto newExecutor, BindingResult bindingResult) {
        Executor addedExecutor = executorService.createExecutor(dtoMapper.toEntity(newExecutor));
        return ResponseEntity.ok().body(dtoMapper.toDto(addedExecutor));
    }

    @Operation(summary = "Обновить исполнителя")
    @ApiResponse(responseCode = "200", description = "Update the executor",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExecutorDto.class))})
    @PutMapping("/{id}")
    public ResponseEntity<ExecutorDto> updateExecutor(@Parameter(description = "id of executor to be updated")
                                                      @PathVariable("id") Long id, @RequestBody ExecutorDto executor) {
        Executor updateExecutor = executorService.updateExecutor(id, dtoMapper.toEntity(executor));
        return ResponseEntity.ok().body(dtoMapper.toDto(updateExecutor));
    }

    @Operation(summary = "Удалить исполнителя по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExecutor(@Parameter(description = "id of executor to be deleted")
                                                 @PathVariable("id") Long id) {
        executorService.deleteExecutor(id);
        return ResponseEntity.ok().body("Executor " + id + " is successfully deleted!");
    }

    @Operation(summary = "Получить исполнителя по возрасту")
    @GetMapping("/age/{age}")
    public ResponseEntity<List<ExecutorDto>> getExecutorsByAge(@Parameter(description = "age of executor to be filtered")
                                                               @PathVariable("age") String age) {
        List<ExecutorDto> executors = executorService.getExecutorsByAge(Integer.parseInt(age))
                .stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(executors);
    }

    @Operation(summary = "Получить исполнителя по имени")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<ExecutorDto>> getExecutorsByName(@Parameter(description = "name of executor to be filtered")
                                                                @PathVariable("name") String name) {
        List<ExecutorDto> executors = executorService.getExecutorsByName(name)
                .stream().map(dtoMapper::toDto).toList();
        return ResponseEntity.ok().body(executors);
    }
}