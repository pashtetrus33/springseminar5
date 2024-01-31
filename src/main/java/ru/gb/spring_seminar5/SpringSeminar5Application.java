package ru.gb.spring_seminar5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
ДЗ №5
Условие:
Вам предстоит создать приложение для управления списком задач с использованием Spring Boot и Spring Data JPA. Требуется реализовать следующие функции:

1. Добавление задачи.
2. Просмотр всех задач.
3. Просмотр задач по статусу (например, "завершена", "в процессе", "не начата").
4. Изменение статуса задачи.
5. Удаление задачи.

Структура задачи:

- ID (автоинкрементное)
- Описание (не может быть пустым)
- Статус (одно из значений: "не начата", "в процессе", "завершена")
- Дата создания (автоматически устанавливается при создании задачи)
 */
@SpringBootApplication
public class SpringSeminar5Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSeminar5Application.class, args);
    }

}
