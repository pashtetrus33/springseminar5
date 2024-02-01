package ru.gb.spring_seminar5.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.models.Status;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    /**
     * Уникальный идентификатор задачи.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotEmpty(message = "Заголовок не может быть пустым!")
    private String title;
    /**
     * Описание задачи.
     */
    @NotEmpty(message = "Описание не может быть пустым!")
    private String description;
    /**
     * Время создания задачи.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime createdAt;
    /**
     * Статус задачи.
     */
    private Status status;

    private Executor executor;
}
