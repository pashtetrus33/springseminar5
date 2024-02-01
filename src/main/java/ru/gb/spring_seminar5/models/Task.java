package ru.gb.spring_seminar5.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность"Задача"
 *
 * @id -идентификатор
 * @name -наименование(заголовок)
 * @description -описание
 * @status -статус
 * @createdDate -дата создания
 */

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50, message = "Заголовок от 2 до 50 символов")
    @Column(name = "title", nullable = false)
    private String title;

    @NotBlank
    @Size(min = 2, max = 150, message = "Описание от 2 до 150 символов")
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "date_creation")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    private Executor executor;
}
