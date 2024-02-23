package ru.gb.spring_seminar5.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.gb.spring_seminar5.models.Executor;
import ru.gb.spring_seminar5.models.Status;

import java.time.LocalDateTime;

@Data
public class TaskDto {

    private TaskDto(TaskDtoBuilder taskDtoBuilder) {
        this.id = taskDtoBuilder.id;
        this.title = taskDtoBuilder.title;
        this.description = taskDtoBuilder.description;
        this.createdAt = taskDtoBuilder.createdAt;
        this.status = taskDtoBuilder.status;
        this.executor = taskDtoBuilder.executor;
    }

    public static final class TaskDtoBuilder {

        private Long id;
        private String title;
        private String description;
        private LocalDateTime createdAt;
        private Executor executor;
        private Status status;

        public TaskDtoBuilder() {
        }

        public TaskDtoBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TaskDtoBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public TaskDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TaskDtoBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TaskDtoBuilder withExecutor(Executor executor) {
            this.executor = executor;
            return this;
        }

        public TaskDtoBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }
    }

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
