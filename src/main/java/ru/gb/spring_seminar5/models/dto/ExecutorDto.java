package ru.gb.spring_seminar5.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.gb.spring_seminar5.models.Status;

import java.time.LocalDateTime;

@Data
public class ExecutorDto {
    /**
     * Уникальный идентификатор исполнителя.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Имя не может быть пустым!")
    private String name;
    /**
     * Описание задачи.
     */
    @NotNull
    private int age;
}
