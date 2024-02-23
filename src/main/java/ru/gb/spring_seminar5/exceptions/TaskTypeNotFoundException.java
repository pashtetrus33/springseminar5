package ru.gb.spring_seminar5.exceptions;

/**
 * Исключение при отсутствии задачи.
 */
public class TaskTypeNotFoundException extends RuntimeException {
    /**
     * Конструктор родительского класса.
     *
     * @param message сообщение об ошибке
     */
    public TaskTypeNotFoundException(String message) {
        super(message);
    }
}
