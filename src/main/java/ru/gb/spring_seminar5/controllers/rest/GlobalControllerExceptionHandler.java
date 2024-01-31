package ru.gb.spring_seminar5.controllers.rest;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gb.spring_seminar5.exceptions.TaskNotFoundException;

/**
 * Класс перехватывающий исключения
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Обработка исключения при отсутствии задачи.
     *
     * @param e объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerTaskNotFound(TaskNotFoundException e) {
        return e.getMessage();
    }
}
