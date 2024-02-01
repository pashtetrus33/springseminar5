package ru.gb.spring_seminar5.controllers.rest;


import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gb.spring_seminar5.exceptions.ExecutorNotFoundException;
import ru.gb.spring_seminar5.exceptions.TaskNotFoundException;

/**
 * Класс перехватывающий исключения
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    /**
     * Обработка исключения при отсутствии задачи.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerTaskNotFound(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения при отсутствии исполнителя.
     *
     * @param ex объект исключения.
     * @return ответ с ошибкой.
     */
    @ExceptionHandler(ExecutorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handlerExecutorNotFound(RuntimeException ex) {

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Обработка исключения при отсутствии некорректном запросе.
     *
     * @param ex обьект исключения
     * @return ответ с ошибкой
     */
    @ExceptionHandler(ConversionFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleConversion(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
