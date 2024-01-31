package ru.gb.spring_seminar5.exceptions;

/**
 * Исключение при отсутствии задачи.
 */
public class TaskNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public TaskNotFoundException(String message) {
        super(message);
    }
}
